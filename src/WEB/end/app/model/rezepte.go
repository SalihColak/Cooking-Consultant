package model

import (
	"database/sql"
	"strconv"
	"strings"

	//_ "github.com/go-sql-driver/mysql" Treiber für den Zugriff auf die Datenbank
	_ "github.com/go-sql-driver/mysql"
)

//RezeptMin Struktur eines Rezept mit minimalen Informationen
type RezeptMin struct {
	Id   int
	Name string
	Bild string
}

//Rezept Struktur eines Rezept
type Rezept struct {
	ID             string
	Name           string
	Art            string
	Anlass         string
	Praeferenz     string
	Kochzeit       int
	Beschreibung   string
	AnzahlZutaten  int
	Zutaten        []Zutat
	AnzahlSchritte int
	Schritte       []string
	Bild           string
}

var btDB *sql.DB

func init() {
	var err error
	btDB, err = sql.Open("mysql", "root:@tcp(127.0.0.1:3306)/syp14")
	if err != nil {
		panic(err.Error())
	}
}

//GetDBCon Getter Datenbankconnection
func GetDBCon() *sql.DB {
	return btDB
}

//SetDBCon Setter Datenbankconnection
func SetDBCon(db *sql.DB) {
	btDB = db
}

//GetAlleRezepte Methode zum Abfragen aller Rezepte in der Datenbaank
//@return {[]RezeptMin} Rückgabe aller Rezepte in der Datenbank
func GetAlleRezepte() []RezeptMin {
	results, err := btDB.Query("SELECT rezid,name,bild from rezept")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var ret []RezeptMin
	var temp RezeptMin
	for results.Next() {
		err = results.Scan(&temp.Id, &temp.Name, &temp.Bild)
		if err != nil {
			panic(err.Error())
		}
		ret = append(ret, temp)
	}
	return ret
}

//LengthRezepte Methode zum Abfragen der Menge an Rezepte in der Datenbank
//@return {int} Rückgabe der Anzahl an Rezepte in der Datenbank
func LengthRezepte() int {
	results, err := btDB.Query("SELECT COUNT(*) as count FROM rezept")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var count int
	for results.Next() {
		results.Scan(&count)
	}
	return count
}

//InsertRezept Methode zum hinzufuegen eines Rezept in die Datenbank
//@param {Rezept} r Einzufügendes Rezept
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func InsertRezept(r Rezept) bool {
	ret := false
	zutaten := r.Zutaten
	zutatenname := make([]string, len(zutaten))
	zutatenmenge := make([]string, len(zutaten))
	zutateneinheit := make([]string, len(zutaten))

	for i := 0; i < len(zutaten); i++ {
		zutatenname[i] = zutaten[i].Name
		zutatenmenge[i] = strconv.Itoa(zutaten[i].Menge)
		zutateneinheit[i] = zutaten[i].Einheit
	}

	menge := strings.Join(zutatenmenge, ";")

	trans, _ := btDB.Begin()

	stmt, err := trans.Prepare("INSERT INTO `rezept` (`rezid`, `name`, `schritte`, `menge`, `kochzeit`, `art`, `anlass`, `praeferenz`, `bild`, `beschreibung`) VALUES (?,?,?,?,?,?,?, ?, ?, ?);")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res, err := stmt.Exec("NULL", r.Name, strings.Join(r.Schritte, ";"), menge, r.Kochzeit, r.Art, r.Anlass, r.Praeferenz, r.Bild, r.Beschreibung)
	id, err := res.LastInsertId()
	ids := InsertZutaten(r.Zutaten, trans)

	for i := 0; i < len(ids); i++ {

		stmt, err := trans.Prepare("INSERT INTO `rezept2zutat` (`rez2zutid`, `rezid`, `zutid`) VALUES (?,?,?);")
		if err != nil {
			println(err.Error())
			trans.Rollback()
			return ret
		}
		stmt.Exec("NULL", id, ids[i])
	}
	defer stmt.Close()
	trans.Commit()
	ret = true
	return ret

}

//GetRezeptById Methode zum Abfragen eines Rezept
//@param {string} id Id des anzuzeigenden Rezept
//@return {Rezept} Rueckgabe des angeforderten Rezept
func GetRezeptById(id string) Rezept {
	var rezept Rezept
	var menge string
	var idd string
	var schritte []uint8

	stmt, err := btDB.Prepare("SELECT * FROM `rezept` where rezid=?")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Query(id)
	for res.Next() {
		err = res.Scan(&idd, &rezept.Name, &schritte, &menge, &rezept.Kochzeit, &rezept.Art, &rezept.Anlass, &rezept.Praeferenz, &rezept.Bild, &rezept.Beschreibung)
		if err != nil {
			panic(err.Error())
		}
	}

	rezept.Anlass = strings.Title(strings.ToLower(rezept.Anlass))
	rezept.Praeferenz = strings.Title(strings.ToLower(rezept.Praeferenz))
	rezept.Art = strings.Title(strings.ToLower(rezept.Art))

	s := string(schritte[:])
	rezept.Schritte = strings.Split(s, ";")

	stmt, err = btDB.Prepare("SELECT zutid FROM `rezept2zutat` where rezid=?")
	if err != nil {
		panic(err.Error())
	}
	res, err = stmt.Query(idd)
	var ids []string
	var temp string
	for res.Next() {
		err = res.Scan(&temp)
		ids = append(ids, temp)
	}

	zutaten := GetZutaten(ids)

	mengen := strings.Split(menge, ";")
	for i := 0; i < len(zutaten); i++ {
		j, _ := strconv.Atoi(mengen[i])
		zutaten[i].Menge = j
	}
	rezept.Zutaten = zutaten
	//fmt.Printf("%+v", zutaten)
	//fmt.Printf("%+v", rezept)
	rezept.ID = idd
	return rezept
}

//DeleteRezeptById Methode zum loeschen eines Rezept in der Datenbank
//@param {string} id ID des zu loeschenden Rezepz
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func DeleteRezeptById(id string) bool {
	ret := false
	trans, _ := btDB.Begin()
	stmt, err := trans.Prepare("SELECT zutid FROM `rezept2zutat` where rezid=?")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res, err := stmt.Query(id)
	var ids []string
	var temp string
	for res.Next() {
		err = res.Scan(&temp)
		ids = append(ids, temp)
	}

	stmt, err = trans.Prepare("DELETE FROM `rezept2zutat` where rezid=?")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	_, err = stmt.Exec(id)
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}

	okay := DeleteZutaten(ids, trans)
	if !okay {
		trans.Rollback()
		return ret
	}

	stmt, err = trans.Prepare("DELETE FROM `rezept` where rezid=?")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res1, err := stmt.Exec(id)
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}

	count, err := res1.RowsAffected()
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	if count == 0 {
		trans.Rollback()
		return ret
	}
	trans.Commit()
	ret = true
	return ret

}

//LastRezepte Methode zum Abfragen der 5 letzten Rezepte die eingefügt worden
//@return {[]string} Rüchgabe der Namen der 5 letzt eingefügten Rezpte
func LastRezepte() []string {
	results, err := btDB.Query("select name from rezept order by rezid desc limit 5;")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var ret []string
	var temp string
	for results.Next() {
		err = results.Scan(&temp)
		if err != nil {
			panic(err.Error())
		}
		ret = append(ret, temp)
	}
	return ret
}

//UpdateRezeptInDB Methode zum updaten eines Rezept
//@param {Rezept} r Zu aktualisierendes Rezept
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func UpdateRezeptInDB(r Rezept) bool {
	trans, _ := btDB.Begin()
	ret := false
	zutaten := r.Zutaten
	zutatenname := make([]string, len(zutaten))
	zutatenmenge := make([]string, len(zutaten))
	zutateneinheit := make([]string, len(zutaten))
	zutatenid := make([]string, len(zutaten))

	for i := 0; i < len(zutaten); i++ {
		zutatenname[i] = zutaten[i].Name
		zutatenmenge[i] = strconv.Itoa(zutaten[i].Menge)
		zutateneinheit[i] = zutaten[i].Einheit
		zutatenid[i] = zutaten[i].ID

	}

	menge := strings.Join(zutatenmenge, ";")

	temp := UpdateZutaten(zutaten, trans)

	if temp {

		stmt, err := trans.Prepare("update rezept set name=?, schritte=?, menge=?, kochzeit=?, art=?, anlass=?, praeferenz=?, bild=?, beschreibung=? where rezid=?;")
		if err != nil {
			println(err.Error())
			trans.Rollback()
			return ret
		}
		res, err := stmt.Exec(r.Name, strings.Join(r.Schritte, ";"), menge, r.Kochzeit, r.Art, r.Anlass, r.Praeferenz, r.Bild, r.Beschreibung, r.ID)
		if err != nil {
			println(err.Error())
			trans.Rollback()
			return ret
		}
		defer stmt.Close()
		count, err := res.RowsAffected()
		if err != nil {
			trans.Rollback()
			return ret
		}
		if count == 0 {
			trans.Rollback()
			return ret
		}
		trans.Commit()
		ret = true
		return ret
	} else {
		return ret
	}
}
