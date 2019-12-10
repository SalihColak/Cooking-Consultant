package controller

import (
	"html/template"
	"net/http"
	"syp/proto/app/model"
)

func LoginUser(w http.ResponseWriter, r *http.Request) {
	var t1 = template.Must(template.ParseFiles("static/template/login.tmpl"))
	t1.ExecuteTemplate(w, "login", nil)
}

func AddUser(w http.ResponseWriter, r *http.Request) {
	anrede := r.FormValue("anrede")
	email := r.FormValue("email")
	//emailW := r.FormValue("emailW")
	passwort := r.FormValue("passwort")
	//passwortW := r.FormValue("passwortW")
	geburtsdatum := r.FormValue("geburtsdatum")
	name := r.FormValue("name")
	vorname := r.FormValue("vorname")
	geschlecht := r.FormValue("geschlecht")
	//datenschutz := r.FormValue("datenschutz")
	//newsletter := r.FormValue("newsletter")

	nutzer := model.User{
		Titel:        anrede,
		Vorname:      vorname,
		Name:         name,
		Geschlecht:   geschlecht,
		Geburtsdatum: geburtsdatum,
		Email:        email,
		Passwort:     passwort,
		Admin:        "0",
	}
	model.AddUserToDB(nutzer)
}
