package model

import (
	"reflect"
	"testing"

	_ "github.com/go-sql-driver/mysql"
)

func TestInsertRezept(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `rezept`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE rezept AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	zutat := []Zutat{Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := Rezept{
		Name:           "Test",
		Art:            "BRUNCH",
		Anlass:         "FREUNDE",
		Praeferenz:     "DEUTSCH",
		Kochzeit:       20,
		Beschreibung:   "Test",
		AnzahlZutaten:  1,
		Zutaten:        zutat,
		AnzahlSchritte: 1,
		Schritte:       schritte,
	}

	type args struct {
		r Rezept
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF108
		{name: "Rezept",
			args: args{r: rezept},
			want: true},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := InsertRezept(tt.args.r); got != tt.want {
				t.Errorf("InsertRezept() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestGetRezeptById(t *testing.T) {
	var rezeptLeer Rezept
	rezeptLeer = GetRezeptById("0")
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `rezept`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE rezept AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	zutat := []Zutat{Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := Rezept{
		Name:           "Test",
		Art:            "Brunch",
		Anlass:         "Freunde",
		Praeferenz:     "Deutsch",
		Kochzeit:       20,
		Beschreibung:   "Test",
		AnzahlZutaten:  1,
		Zutaten:        zutat,
		AnzahlSchritte: 1,
		Schritte:       schritte,
	}
	InsertRezept(rezept)
	rezept.ID = "1"
	rezept.Zutaten[0].ID = "1"
	rezept.AnzahlSchritte = 0
	rezept.AnzahlZutaten = 0
	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want Rezept
	}{
		// TODO: Add test cases.
		//TF109
		{name: "Rezept existiert",
			args: args{id: rezept.ID},
			want: rezept},
		//TF110
		{name: "Rezept existiert nicht",
			args: args{id: "0"},
			want: rezeptLeer},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetRezeptById(tt.args.id); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetRezeptById() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestDeleteRezeptById(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `rezept`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE rezept AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	zutat := []Zutat{Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := Rezept{
		Name:           "Test",
		Art:            "Brunch",
		Anlass:         "Freunde",
		Praeferenz:     "Deutsch",
		Kochzeit:       20,
		Beschreibung:   "Test",
		AnzahlZutaten:  1,
		Zutaten:        zutat,
		AnzahlSchritte: 1,
		Schritte:       schritte,
	}
	InsertRezept(rezept)
	rezept.ID = "1"
	rezept.Zutaten[0].ID = "1"
	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF111
		{name: "Rezept existiert",
			args: args{id: rezept.ID},
			want: true,
		},
		//TF112
		{name: "Rezept existiert nicht",
			args: args{id: "0"},
			want: true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			DeleteRezeptById(tt.args.id)
		})
	}
	EndTest()
}

func TestUpdateRezeptInDB(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `rezept2zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `zutat`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("DELETE FROM `rezept`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE zutat AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE rezept AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	zutat := []Zutat{Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := Rezept{
		Name:           "Test",
		Art:            "Brunch",
		Anlass:         "Freunde",
		Praeferenz:     "Deutsch",
		Kochzeit:       20,
		Beschreibung:   "Test",
		AnzahlZutaten:  1,
		Zutaten:        zutat,
		AnzahlSchritte: 1,
		Schritte:       schritte,
	}
	rezept2 := Rezept{
		Name:           "Test",
		Art:            "Brunch",
		Anlass:         "Freunde",
		Praeferenz:     "Deutsch",
		Kochzeit:       20,
		Beschreibung:   "Test",
		AnzahlZutaten:  1,
		Zutaten:        zutat,
		AnzahlSchritte: 1,
		Schritte:       schritte,
	}
	InsertRezept(rezept)
	rezept.ID = "1"
	rezept2.ID = "0"
	rezept.Zutaten[0].ID = "1"
	rezept.Beschreibung = "Test2"
	type args struct {
		r Rezept
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF113
		{name: "Rezept existiert",
			args: args{r: rezept},
			want: true},
		//TF114
		{name: "Rezept existiert nicht",
			args: args{r: rezept2},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := UpdateRezeptInDB(tt.args.r); got != tt.want {
				t.Errorf("UpdateRezeptInDB() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}
