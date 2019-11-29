package model

import (
	_ "github.com/go-sql-driver/mysql"
)

type User struct {
	UseID        int
	Titel        string
	Name         string
	Vorname      string
	Geschlecht   string
	Geburtsdatum string
	Email        string
	Passwort     string
	Admin        string
}

func Hallo() {

}

func AddUserToDB(u User) int64 {
	stmt, err := btDB.Prepare("INSERT INTO `benutzer` (`userid`, `titel`, `name`, `vorname`, `geschlecht`, `geburtsdatum`, `email`, `passwort`, `admin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Exec("NULL", u.Titel, u.Name, u.Vorname, u.Geschlecht, u.Geburtsdatum, u.Email, u.Passwort, u.Admin)
	id, err := res.LastInsertId()
	return id
}
