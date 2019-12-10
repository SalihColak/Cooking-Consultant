package model

import (
	"database/sql"
	"strconv"
	"strings"

	_ "github.com/go-sql-driver/mysql"
)

type RezeptMin struct {
	Id   int
	Name string
	Bild string
}

type Rezept struct {
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

func InsertRezept(r Rezept) {
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

	stmt, err := btDB.Prepare("INSERT INTO `rezept` (`rezid`, `name`, `schritte`, `menge`, `kochzeit`, `art`, `anlass`, `praeferenz`, `bild`, `beschreibung`) VALUES (?,?,?,?,?,?,?, ?, ?, ?);")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Exec("NULL", r.Name, strings.Join(r.Schritte, ";"), menge, r.Kochzeit, r.Art, r.Anlass, r.Praeferenz, "logo.png", r.Beschreibung)
	id, err := res.LastInsertId()
	ids := InsertZutaten(r.Zutaten)

	for i := 0; i < len(ids); i++ {

		stmt, err := btDB.Prepare("INSERT INTO `rezept2zutat` (`rez2zutid`, `rezid`, `zutid`) VALUES (?,?,?);")
		if err != nil {
			panic(err.Error())
		}
		stmt.Exec("NULL", id, ids[i])
	}
	defer stmt.Close()

}

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
	return rezept
}
