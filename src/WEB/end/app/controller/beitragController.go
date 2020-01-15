package controller

import (
	"html/template"
	"net/http"
	"strconv"
	"syp/end/app/model"
)

/*BeitragEintragen Methode zum eingeben eines Rezept*/
func BeitragEintragen(w http.ResponseWriter, r *http.Request) {
	temp, err := template.ParseFiles("static/template/addBeitrag.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
	if err != nil {
		w.Header().Set("Location", "/beitragEintragen")
		t, err := template.New("todos").Parse("Es ist ein Fehler beim anzeigen der Seite aufgetretten.")
		if err != nil {
			panic(err)
		}
		err = t.Execute(w, "todos")
		if err != nil {
			panic(err)
		}
		return
	}
	var t1 = template.Must(temp, err)
	t1.ExecuteTemplate(w, "addBeitrag", nil)

}

/*AddBeitrag Methode zum senden eines Rezept an die Datenbank*/
func AddBeitrag(w http.ResponseWriter, r *http.Request) {
	titel := r.FormValue("titel")
	kategorie := r.FormValue("kategorie")
	inhalt := r.FormValue("inhalt")

	if titel == "" || kategorie == "" || inhalt == "" {
		http.Redirect(w, r, "/beitragEintragen", http.StatusFound)
	} else {

		beitrag := model.Beitrag{
			Titel:     titel,
			Kategorie: kategorie,
			Inhalt:    inhalt,
		}

		id := model.AddBeitragToDB(beitrag)
		if id == 0 {
			http.Redirect(w, r, "/beitragEintragen", http.StatusFound)
		} else {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		}

	}
}

/*AlleBeitraege Methode zum Anzeigen aller Beitraege*/
func AlleBeitraege(w http.ResponseWriter, r *http.Request) {
	daten := model.GetAlleBeitraege()
	temp, err := template.ParseFiles("static/template/alleBeitraege.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl",
		"static/template/alleBeitraegeKachel.tmpl")
	if err != nil {
		w.Header().Set("Location", "/alleBeitraege")
		t, err := template.New("todos").Parse("Es ist ein Fehler beim anzeigen der Seite aufgetretten.")
		if err != nil {
			panic(err)
		}
		err = t.Execute(w, "todos")
		if err != nil {
			panic(err)
		}
		return
	}
	var t1 = template.Must(temp, err)
	t1.ExecuteTemplate(w, "alleBeitraege", daten)

}

/*DeleteBeitrag Methode zum löschen eines Rezept in der Datenbank*/
func DeleteBeitrag(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
	} else {
		temp := model.DeleteBeitragById(id)
		if temp {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		} else {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		}
	}
}

/*GetBeitrag Methode zum Anzeigen eines Beitrag*/
func GetBeitrag(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
	} else {
		daten := model.GetBeitragById(id)
		if daten.BeitID == 0 {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		} else {
			temp, err := template.ParseFiles("static/template/beitrag.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", "/beitrag")
				t, err := template.New("todos").Parse("Es ist ein Fehler beim anzeigen der Seite aufgetretten.")
				if err != nil {
					panic(err)
				}
				err = t.Execute(w, "todos")
				if err != nil {
					panic(err)
				}
				return
			}
			var t1 = template.Must(temp, err)
			t1.ExecuteTemplate(w, "beitrag", daten)
		}
	}
}

/*BeitragBearbeiten Methode zum bearbeiten eines Beitrag*/
func BeitragBearbeiten(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
	} else {
		daten := model.GetBeitragById(id)
		if daten.BeitID == 0 {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		} else {
			temp, err := template.ParseFiles("static/template/updateBeitrag.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", "/beitragBearbeiten?id="+id)
				t, err := template.New("todos").Parse("Es ist ein Fehler beim anzeigen der Seite aufgetretten.")
				if err != nil {
					panic(err)
				}
				err = t.Execute(w, "todos")
				if err != nil {
					panic(err)
				}
				return
			}
			var t1 = template.Must(temp, err)
			t1.ExecuteTemplate(w, "updateBeitrag", daten)
		}
	}
}

/*UpdateBeitrag Methode zum senden eines geaenderten Beitrag an die Datenbank*/
func UpdateBeitrag(w http.ResponseWriter, r *http.Request) {
	titel := r.FormValue("titel")
	kategorie := r.FormValue("kategorie")
	inhalt := r.FormValue("inhalt")
	id, _ := strconv.Atoi(r.FormValue("id"))
	if titel == "" || kategorie == "" || inhalt == "" || id <= 0 {
		http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
	} else {
		beitrag := model.Beitrag{
			Titel:     titel,
			Kategorie: kategorie,
			Inhalt:    inhalt,
			BeitID:    id,
		}
		println(beitrag.Titel)
		temp := model.UpdateBeitragToDB(beitrag)
		if temp {
			http.Redirect(w, r, "/alleBeitraege", http.StatusFound)
		} else {
			http.Redirect(w, r, "/beitragBearbeiten?id="+strconv.Itoa(id), http.StatusFound)
		}
	}
}
