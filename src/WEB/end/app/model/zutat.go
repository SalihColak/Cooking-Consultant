package model

import (
	"database/sql"
	//_ "github.com/go-sql-driver/mysql" Treiber für den Zugriff auf die Datenbank
	_ "github.com/go-sql-driver/mysql"
)

//Zutat Struktur einer Zutat
type Zutat struct {
	ID      string
	Name    string
	Menge   int
	Einheit string
}

//InsertZutaten Methode zum hinzufügen von Zutaten in die Datenbank
//@param ([]Zutat) z Zu hinzufuegende Zutaten
//@param (*sql.Tx) trans Transaktion aus der Aufruffenden Methode
//@return ([]int64) Rückgabe der ID's der hinzugefügten Zutaten
func InsertZutaten(z []Zutat, trans *sql.Tx) []int64 {
	ids := make([]int64, len(z))

	for i := 0; i < len(z); i++ {
		stmt, err := trans.Prepare("INSERT INTO `zutat` (`zutid`, `name`, `einheit`, `bild`) VALUES (?,?,?,?);")
		if err != nil {
			println(err.Error())
			trans.Rollback()
		}
		res, err := stmt.Exec("NULL", z[i].Name, z[i].Einheit, "food-placeholder.png")
		id, err := res.LastInsertId()
		ids[i] = id
	}
	return ids
}

//GetZutaten Methode zum Abfrage von Zutaten aus der Datenbank
//@param ([]string) ids ID's der zu abfragenden Zutaten
//@return ([]Zutat) Rückgabe der zu Zutaten
func GetZutaten(ids []string) []Zutat {
	var zutaten []Zutat
	var temp Zutat
	for i := 0; i < len(ids); i++ {
		stmt, err := btDB.Prepare("SELECT zutid, name, einheit from zutat where zutid=?")
		if err != nil {
			panic(err.Error())
		}
		res, err := stmt.Query(ids[i])
		for res.Next() {
			res.Scan(&temp.ID, &temp.Name, &temp.Einheit)
			zutaten = append(zutaten, temp)
		}
	}

	return zutaten
}

//DeleteZutaten Methode zum loeschen von zutaten
//@param ([]string) ids ID's der zu löschenden Zutaten
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func DeleteZutaten(ids []string, trans *sql.Tx) bool {
	ret := false
	for i := 0; i < len(ids); i++ {
		stmt, err := trans.Prepare("DELETE from zutat where zutid=?")
		if err != nil {
			println(err.Error())

			return ret
		}
		res, err := stmt.Exec(ids[i])
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
			trans.Rollback()
			return ret
		}
	}

	ret = true
	return ret

}

//UpdateZutaten Methode zum updaten von Zutaten
//@param ([]Zutat) z Zu aktualierende Zutaten
//@param (*sql.Tx) trans Transaktion aus der Aufruffenden Methode
//@return (bool) Rückgabe ob das Löschen erfolgreich oder nicht erfolgreich war
func UpdateZutaten(z []Zutat, trans *sql.Tx) bool {
	ret := false
	for i := 0; i < len(z); i++ {
		stmt, err := trans.Prepare("update zutat set name = ?, einheit = ? where zutid=?;")
		if err != nil {
			println(err.Error())
			trans.Rollback()
			return ret
		}
		_, err = stmt.Exec(z[i].Name, z[i].Einheit, z[i].ID)
		if err != nil {
			println(err.Error())
			trans.Rollback()
			return ret
		}
	}
	ret = true
	return ret
}
