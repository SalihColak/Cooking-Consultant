package model

import (
	//_ "github.com/go-sql-driver/mysql" Treiber für den Zugriff auf die Datenbank
	_ "github.com/go-sql-driver/mysql"
)

const key = "empty"

//User Struktur einer Zutat
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

//UserMin Struktur eine User mit minimalen Daten
type UserMin struct {
	UseId   int
	Vorname string
	Name    string
	Email   string
}

/*AddUserToDB Methode zum einfügen eines Users in die Datenbank*/
//@param {User} u Element welches in die Datenbank eingefügt wird
//@return {int64} Rückgabe ID des eingefügten User
func AddUserToDB(u User) int64 {
	trans, _ := btDB.Begin()
	stmt, err := trans.Prepare("INSERT INTO `benutzer` (`userid`, `titel`, `name`, `vorname`, `geschlecht`, `geburtsdatum`, `email`, `passwort`, `admin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	res, err := stmt.Exec("NULL", u.Titel, u.Name, u.Vorname, u.Geschlecht, u.Geburtsdatum, u.Email, u.Passwort, u.Admin)
	id, err := res.LastInsertId()
	trans.Commit()
	return id
}

//LengthUser Methode zum anzeigen der registrierten Benutzer in der Datenbank
//@return {int} Anzahl der Benutzer in der Datenbank
func LengthUser() int {
	results, err := btDB.Query("SELECT COUNT(*) as count FROM benutzer")
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

//GetUser Methode um einen Benutzer abzufragen anhand der Email
//@param {string} email Email des zu suchenden Benutzer
//@return {User} Angeforderter User
func GetUser(email string) User {
	var user User

	stmt, err := btDB.Prepare("SELECT * FROM `benutzer` WHERE email=?")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Query(email)
	for res.Next() {
		err = res.Scan(&user.UseID, &user.Titel, &user.Name, &user.Vorname, &user.Geschlecht, &user.Geburtsdatum, &user.Email, &user.Passwort, &user.Admin)
		if err != nil {
			panic(err.Error())
		}
	}

	return user
}

//GetAdminState Methode zum Abfragen ob eine Nutzer Admin Status besitzt
//@param {string} id Id des abzufragenden Benutzer
//@return {string} Status des abzufragenden Benutzer
func GetAdminState(id string) string {
	var admin string

	stmt, err := btDB.Prepare("SELECT admin FROM `benutzer` WHERE userid=?")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Query(id)
	for res.Next() {
		err = res.Scan(&admin)
		if err != nil {
			panic(err.Error())
		}
	}

	return admin
}

//GetAlleUser Methode zum abfragen aller Benutzer
//@return {[]UserMin} Alle in der Datenbank befindlichen Benutzer
func GetAlleUser() []UserMin {
	results, err := btDB.Query("SELECT userid, vorname, name, email FROM `benutzer` WHERE admin=0")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var ret []UserMin
	var temp UserMin
	for results.Next() {
		err = results.Scan(&temp.UseId, &temp.Vorname, &temp.Name, &temp.Email)
		if err != nil {
			panic(err.Error())
		}
		ret = append(ret, temp)
	}
	return ret

}

//GetAlleAdmin Methode zum abfragen aller Benutzer
//@return {[]UserMin} Alle in der Datenbank befindlichen Admin
func GetAlleAdmin() []UserMin {
	results, err := btDB.Query("SELECT userid, vorname, name, email FROM `benutzer` WHERE admin=1")
	if err != nil {
		panic(err.Error())
	}

	defer results.Close()
	var ret []UserMin
	var temp UserMin
	for results.Next() {
		err = results.Scan(&temp.UseId, &temp.Vorname, &temp.Name, &temp.Email)
		if err != nil {
			panic(err.Error())
		}
		ret = append(ret, temp)
	}
	return ret

}

/*
func SendPush(title string, body string) {
	msg := &fcm.Message{
		To: "/topics/all",
		Notification: &fcm.Notification{
			Title: title,
			Body:  body,
			Icon:  "ic_launcher",
		},
	}

	// Create a FCM client to send the message.
	client, err := fcm.NewClient(key)
	if err != nil {
		log.Fatalln(err)
	}

	// Send the message and receive the response without retries.
	response, err := client.Send(msg)
	if err != nil {
		log.Fatalln(err)
	}

	log.Printf("%#v\n", response)

}*/

//DeleteUser Methode zum loeschen eines Benutzer in der Datenbank
//@param {string} id Id des zu loeschenden Benutzer
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func DeleteUser(id string) bool {
	ret := false
	trans, _ := btDB.Begin()
	stmt, err := trans.Prepare("DELETE FROM `benutzer2rezept` where userid=?")
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

	stmt, err = trans.Prepare("DELETE FROM `einkaufsliste2zutatstate` where einkid IN (SELECT einkid FROM einkaufsliste WHERE userid =?)")
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

	stmt, err = trans.Prepare("DELETE FROM `einkaufsliste` where userid=?")
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

	stmt, err = trans.Prepare("DELETE FROM `benutzer` where userid=?")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res, err := stmt.Exec(id)
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	count, err := res.RowsAffected()
	trans.Commit()
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
	return ret
}

//GetUserByID Methode um einen Benutzer abzufragen anhand der ID
//@param {string} id Id des zu suchenden Benutzer
//@return {User} Angeforderter User
func GetUserByID(id string) User {
	var user User

	stmt, err := btDB.Prepare("SELECT * FROM `benutzer` WHERE userid=?")
	if err != nil {
		panic(err.Error())
	}
	res, err := stmt.Query(id)
	for res.Next() {
		err = res.Scan(&user.UseID, &user.Titel, &user.Name, &user.Vorname, &user.Geschlecht, &user.Geburtsdatum, &user.Email, &user.Passwort, &user.Admin)
		if err != nil {
			panic(err.Error())
		}
	}

	return user
}

//UpdateUserToDB Methode zum updaten eine Benutzer
//@param {User} u Zu aktualisierender Benutzer
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func UpdateUserToDB(u User) bool {
	ret := false
	trans, _ := btDB.Begin()
	stmt, err := trans.Prepare("UPDATE `benutzer` set name = ?, vorname = ?, email = ?, passwort = ? where userid = ? ;")
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	res, err := stmt.Exec(u.Name, u.Vorname, u.Email, u.Passwort, u.UseID)
	if err != nil {
		println(err.Error())
		trans.Rollback()
		return ret
	}
	count, err := res.RowsAffected()
	trans.Commit()
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
	return ret
}
