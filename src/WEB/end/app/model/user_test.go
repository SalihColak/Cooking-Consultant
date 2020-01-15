package model

import (
	"database/sql"
	"reflect"
	"strconv"
	"testing"

	_ "github.com/go-sql-driver/mysql"
)

var prodDB *sql.DB

func BeginnTest() {
	testDB, err := sql.Open("mysql", "root:@tcp(127.0.0.1:3306)/sypsave")
	if err != nil {
		panic(err.Error())
	}
	prodDB = btDB
	btDB = testDB
}

func EndTest() {
	btDB = prodDB
}

func TestAddUserToDB(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `benutzer`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE benutzer AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	nutzer := User{
		Titel:        "HERR",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp14@email.de",
		Passwort:     "456",
		Admin:        "0",
	}
	type args struct {
		u User
	}
	tests := []struct {
		name string
		args args
		want int64
	}{
		// TODO: Add test cases.
		//TF115
		{name: "Nutzer korrekt",
			args: args{u: nutzer},
			want: 1,
		},
	}
	for _, tt := range tests {

		t.Run(tt.name, func(t *testing.T) {

			if got := AddUserToDB(tt.args.u); got != tt.want {
				t.Errorf("AddUserToDB() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestGetUser(t *testing.T) {
	var user User
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `benutzer`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE benutzer AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	nutzer := User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp14@email.de",
		Passwort:     "456",
		Admin:        "0",
	}
	id := AddUserToDB(nutzer)
	nutzer.UseID = int(id)
	type args struct {
		email string
	}
	tests := []struct {
		name string
		args args
		want User
	}{
		// TODO: Add test cases.
		//TF116
		{name: "Nutzer existiert",
			args: args{email: nutzer.Email},
			want: nutzer},
		//TF117
		{name: "Nutzer existiert nicht",
			args: args{email: "laLa"},
			want: user},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetUser(tt.args.email); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetUser() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestDeleteUser(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `benutzer`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE benutzer AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	nutzer := User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp14@email.de",
		Passwort:     "456",
		Admin:        "0",
	}
	id := AddUserToDB(nutzer)
	nutzer.UseID = int(id)

	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF120
		{name: "Nutzer existiert",
			args: args{id: strconv.Itoa(nutzer.UseID)},
			want: true},
		//TF121
		{name: "Nutzer existiert nicht",
			args: args{id: strconv.Itoa(0)},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := DeleteUser(tt.args.id); got != tt.want {
				t.Errorf("DeleteUser() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestGetUserByID(t *testing.T) {
	var user User
	BeginnTest()
	nutzer := User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp14@email.de",
		Passwort:     "456",
		Admin:        "0",
	}
	id := AddUserToDB(nutzer)
	nutzer.UseID = int(id)
	type args struct {
		id string
	}
	tests := []struct {
		name string
		args args
		want User
	}{
		// TODO: Add test cases.
		//TF118
		{name: "Nutzer existiert",
			args: args{id: strconv.Itoa(nutzer.UseID)},
			want: nutzer},
		//TF119
		{name: "Nutzer existiert nicht",
			args: args{id: strconv.Itoa(0)},
			want: user},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetUserByID(tt.args.id); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetUserByID() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}

func TestUpdateUserToDB(t *testing.T) {
	BeginnTest()
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `benutzer`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE benutzer AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
	nutzer := User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp14@email.de",
		Passwort:     "456",
		Admin:        "0",
	}
	id := AddUserToDB(nutzer)
	nutzer.Name = "Test2"
	nutzer.UseID = int(id)
	nutzer2 := User{
		UseID:        0,
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "1991-02-02",
		Email:        "user.syp142@email.de",
		Passwort:     "456",
		Admin:        "0",
	}

	type args struct {
		u User
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		// TODO: Add test cases.
		//TF122
		{name: "Nutzer existiert",
			args: args{u: nutzer},
			want: true},
		//TF123
		{name: "Nutzer existiert nicht",
			args: args{u: nutzer2},
			want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := UpdateUserToDB(tt.args.u); got != tt.want {
				t.Errorf("UpdateUserToDB() = %v, want %v", got, tt.want)
			}
		})
	}
	EndTest()
}
