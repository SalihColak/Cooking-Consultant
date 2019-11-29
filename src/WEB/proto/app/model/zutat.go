package model

import (
	_ "github.com/go-sql-driver/mysql"
)

type Zutat struct {
	Name    string
	Menge   int
	Einheit string
}

func InsertZutaten(z []Zutat) []int64 {
	ids := make([]int64, len(z))

	for i := 0; i < len(z); i++ {
		stmt, err := btDB.Prepare("INSERT INTO `zutat` (`zutid`, `name`, `einheit`, `bild`) VALUES (?,?,?,?);")
		if err != nil {
			panic(err.Error())
		}
		res, err := stmt.Exec("NULL", z[i].Name, z[i].Einheit, "food-placeholder.png")
		id, err := res.LastInsertId()
		ids[i] = id
	}

	return ids
}

func GetZutaten(ids []string) []Zutat {
	var zutaten []Zutat
	var temp Zutat
	for i := 0; i < len(ids); i++ {
		stmt, err := btDB.Prepare("SELECT name, einheit from zutat where zutid=?")
		if err != nil {
			panic(err.Error())
		}
		res, err := stmt.Query(ids[i])
		for res.Next() {
			res.Scan(&temp.Name, &temp.Einheit)
			zutaten = append(zutaten, temp)
		}
	}

	return zutaten
}
