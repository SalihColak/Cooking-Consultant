package model

import (
	//"log"
	//"github.com/appleboy/go-fcm"
	//_ "github.com/go-sql-driver/mysql" Treiber für den Zugriff auf die Datenbank
	_ "github.com/go-sql-driver/mysql"
)

//Beitrag Struktur eines Beitrag
type Beitrag struct {
	BeitID    int
	Titel     string
	Kategorie string
	Inhalt    string
}

//GetAlleBeitraege Methode zum anzeigen aller Beitraege in der Datenbank
//@return{[]Beitrag} Rückgabe aller Beittraege in der Datenbank
func GetAlleBeitraege() []Beitrag {
	results, err := btDB.Query("SELECT * from beitrag")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var ret []Beitrag
	var temp Beitrag
	for results.Next() {
		err = results.Scan(&temp.BeitID, &temp.Titel, &temp.Kategorie, &temp.Inhalt)
		if err != nil {
			panic(err.Error())
		}
		ret = append(ret, temp)
	}
	return ret
}

//AddBeitragToDB Methode zum einfuegen eines Beitrag in dei Datenbank
//@param {Beitrag} b Element welches in die Datenbank eingefuegt wird
//@return {int64} Rueckgabe der ID des eingefuegten Beitrag
func AddBeitragToDB(b Beitrag) int64 {
	trans, _ := btDB.Begin()
	stmt, err := trans.Prepare("INSERT INTO `beitrag` (`beitid`, `titel`, `kategorie`, `inhalt`) VALUES (?, ?, ?, ?);")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return 0
	}
	res, err := stmt.Exec("NULL", b.Titel, b.Kategorie, b.Inhalt)
	id, err := res.LastInsertId()
	defer stmt.Close()
	trans.Commit()
	return id
}

//DeleteBeitragById Methode zum loeschen eines Beitrag in der Datenbank
//@param {string} id ID des zu loeschenden Beitrag
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func DeleteBeitragById(id string) bool {
	ret := false
	stmt, err := btDB.Prepare("DELETE FROM `beitrag` where beitid=?")
	if err != nil {
		println(err.Error())
		return ret
	}
	res, err := stmt.Exec(id)
	if err != nil {
		println(err.Error())
		return ret
	}
	count, err := res.RowsAffected()
	if err != nil {
		println(err.Error())
		return ret
	}
	if count == 0 {
		return ret
	}
	ret = true
	return ret
}

//LengthBeitrag Methode zum Anzeigen der Menge an Beitaegen in der Datenbank
//@return {int} Rueckgabe der Anzahl an in der Datenbank befindlichen Beitraege
func LengthBeitrag() int {
	results, err := btDB.Query("SELECT COUNT(*) as count FROM beitrag")
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

//LastBeitrag Methode zum anzeigen der letzten 5 Beitraege die in die Datenbank eingefuegt wurden
//@return {[]string} Rueckgabe der Namen der letzten 5 Beitraege die in die Datenbank eingefuegt wurden
func LastBeitrag() []string {
	results, err := btDB.Query("select titel from beitrag order by beitid desc limit 5;")
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

//GetBeitragById Methode zum abfragen eines Beitrag anhand der ID
//@param {string} id ID des zu abfragenden Beitrag
//@return {Beitrag} Rueckgabe des angefirderten Beitrag
func GetBeitragById(id string) Beitrag {
	var temp Beitrag
	stmt, err := btDB.Prepare("SELECT * FROM `beitrag` where beitid=?")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Query(id)
	for res.Next() {
		err = res.Scan(&temp.BeitID, &temp.Titel, &temp.Kategorie, &temp.Inhalt)
		if err != nil {
			panic(err.Error())
		}
	}
	return temp
}

//UpdateBeitragToDB Methode zu updaten eines Beitrag in der Datenbank
//@param {Beitrag} b Zu aktualisierendes Beitrag
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func UpdateBeitragToDB(b Beitrag) bool {
	trans, _ := btDB.Begin()
	ret := false
	stmt, err := trans.Prepare("UPDATE `beitrag` set titel = ?, kategorie = ?, inhalt = ? where beitid = ? ;")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res, err := stmt.Exec(b.Titel, b.Kategorie, b.Inhalt, b.BeitID)
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	count, err := res.RowsAffected()
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	if count == 0 {
		trans.Rollback()
		return ret
	}
	ret = true
	trans.Commit()
	ret = true
	return ret
}
