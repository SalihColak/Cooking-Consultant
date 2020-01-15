package controller

import (
	"html/template"
	"io"
	"net/http"
	"os"
	"strconv"
	"syp/end/app/model"
)

/*Datenrezept Struktur für die Anzeige eines Rezept in der View*/
type Datenrezept struct {
	Rezept model.Rezept
	Index  []int
}

/*Datenrezeptupdate Struktur für die Anzeige eines Rezeptupdate in der View*/
type Datenrezeptupdate struct {
	Rezept        model.Rezept
	IndexSchritte []int
	IndexZutaten  []int
}

var tmpl *template.Template

/*AlleRezepte Methode zum anzeigen aller Rezepte*/
func AlleRezepte(w http.ResponseWriter, r *http.Request) {
	model.LengthRezepte()
	daten := model.GetAlleRezepte()
	templ, err := template.ParseFiles("static/template/alleRezepte.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl",
		"static/template/alleRezepteKachel.tmpl")
	if err != nil {
		w.Header().Set("Location", "/alleRezepte")
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
	var t1 = template.Must(templ, err)
	t1.ExecuteTemplate(w, "alleRezepte", daten)

}

/*RezeptEintragen Methode zum eintragen eines Reszeptes*/
func RezeptEintragen(w http.ResponseWriter, r *http.Request) {
	templ, err := template.ParseFiles("static/template/addRezept.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
	if err != nil {
		w.Header().Set("Location", "/rezeptEintragen")
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
	var t1 = template.Must(templ, err)
	t1.ExecuteTemplate(w, "addRezept", nil)

}

/*AddZerept Methode zum senden eines Rezeptes an die Datenbank*/
func AddZerept(w http.ResponseWriter, r *http.Request) {
	name := r.FormValue("name")
	art := r.FormValue("art")
	anlass := r.FormValue("anlass")
	praeferenz := r.FormValue("praeferenz")
	zeit, _ := strconv.Atoi(r.FormValue("zeit"))
	beschreibung := r.FormValue("beschreibung")
	anzahlZutaten := r.FormValue("member")
	anzahlSchritte := r.FormValue("memberText")

	if name == "" || art == "" || anlass == "" || praeferenz == "" || beschreibung == "" || anzahlZutaten == "" || anzahlSchritte == "" {
		http.Redirect(w, r, "/addRezept", http.StatusFound)
		return
	} else {

		anzahlZutatenInt, _ := strconv.Atoi(anzahlZutaten)
		zutaten := make([]model.Zutat, anzahlZutatenInt)

		anzahlSchritteInt, _ := strconv.Atoi(anzahlSchritte)
		schritte := make([]string, anzahlSchritteInt)

		for i := 0; i < anzahlSchritteInt; i++ {
			sname := r.FormValue("schritt" + strconv.Itoa(i+1))
			if sname == "" {
				http.Redirect(w, r, "/addRezept", http.StatusFound)
				return
			} else {
				schritte[i] = sname
			}

		}

		for i := 0; i < anzahlZutatenInt; i++ {
			zname := r.FormValue("zutatName" + strconv.Itoa(i+1))
			zmenge, _ := strconv.Atoi(r.FormValue("zutatMenge" + strconv.Itoa(i+1)))
			zeinheit := r.FormValue("zutatEinheit" + strconv.Itoa(i+1))
			if zname == "" || zmenge == 0 || zeinheit == "" {
				//redirect
			} else {
				zutat := model.Zutat{"", zname, zmenge, zeinheit}
				zutaten[i] = zutat
			}

		}

		bild := UploadPicture(r)

		if bild == "" {
			bild = "logo.png"
		}

		rezept := model.Rezept{
			Name:          name,
			Art:           art,
			Anlass:        anlass,
			Praeferenz:    praeferenz,
			Kochzeit:      zeit,
			Beschreibung:  beschreibung,
			AnzahlZutaten: anzahlSchritteInt,
			Zutaten:       zutaten,
			Schritte:      schritte,
			Bild:          bild,
		}

		temp := model.InsertRezept(rezept)
		if temp {
			http.Redirect(w, r, "/alleRezepte", http.StatusFound)
		} else {
			http.Redirect(w, r, "/addRezept", http.StatusFound)
		}
	}
}

/*GetRezept Methode zum anzeigen eines Rezept*/
func GetRezept(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleRezepte", http.StatusFound)
		return
	} else {
		daten := model.GetRezeptById(id)
		id1, _ := strconv.Atoi(daten.ID)
		if id1 <= 0 {
			http.Redirect(w, r, "/alleRezepte", http.StatusFound)
		} else {
			var index []int
			for i := 0; i < len(daten.Schritte); i++ {
				index = append(index, (i + 1))
			}

			datensatz := Datenrezept{
				Rezept: daten,
				Index:  index,
			}

			templ, err := template.ParseFiles("static/template/rezept.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", ("/rezept?id=" + id))
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
			t1 := template.Must(templ, err)
			t1.ExecuteTemplate(w, "rezept", datensatz)
		}
	}
}

/*DeleteRezept Methode zum löschen eines Rezept in der Datenbank*/
func DeleteRezept(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleRezepte", http.StatusFound)
	} else {
		model.DeleteRezeptById(id)
		http.Redirect(w, r, "/alleRezepte", http.StatusFound)
	}
}

/*UpdateRezept Methode zum bearbeiten eines Rezept*/
func UpdateRezept(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleRezepte", http.StatusFound)
		return
	} else {
		daten := model.GetRezeptById(id)
		id1, _ := strconv.Atoi(daten.ID)
		if id1 <= 0 {
			http.Redirect(w, r, "/alleRezepte", http.StatusFound)
			return
		} else {

			var index []int
			for i := 0; i < len(daten.Schritte); i++ {
				index = append(index, (i + 1))
			}

			var index2 []int
			for i := 0; i < len(daten.Zutaten); i++ {
				index2 = append(index2, (i + 1))
			}

			datensatz := Datenrezeptupdate{
				Rezept:        daten,
				IndexSchritte: index,
				IndexZutaten:  index2,
			}

			tmpl, err := template.ParseFiles("static/template/updateRezept.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", ("/rezeptBearbeiten?id=" + id))
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

			var t1 = template.Must(tmpl, err)
			t1.ExecuteTemplate(w, "updateRezept", datensatz)
		}
	}
}

/*UpdateRezeptDB Methode zum aktualisieren eienes Rezept in der Datenbank*/
func UpdateRezeptDB(w http.ResponseWriter, r *http.Request) {
	name := r.FormValue("name")
	art := r.FormValue("art")
	anlass := r.FormValue("anlass")
	praeferenz := r.FormValue("praeferenz")
	zeit, _ := strconv.Atoi(r.FormValue("zeit"))
	beschreibung := r.FormValue("beschreibung")
	anzahlZutaten := r.FormValue("member")
	anzahlSchritte := r.FormValue("memberText")
	id := r.FormValue("id")
	bild := r.FormValue("bild")

	if name == "" || art == "" || anlass == "" || praeferenz == "" || beschreibung == "" || anzahlZutaten == "" || anzahlSchritte == "" || id == "" {
		http.Redirect(w, r, "/alleRezepte", http.StatusFound)
	} else {

		anzahlZutatenInt, _ := strconv.Atoi(anzahlZutaten)
		zutaten := make([]model.Zutat, anzahlZutatenInt)

		anzahlSchritteInt, _ := strconv.Atoi(anzahlSchritte)
		schritte := make([]string, anzahlSchritteInt)

		for i := 0; i < anzahlSchritteInt; i++ {
			sname := r.FormValue("schritt" + strconv.Itoa(i+1))
			if sname == "" {
				http.Redirect(w, r, ("/rezeptBearbeiten?id=" + id), http.StatusFound)
				return
			} else {
				schritte[i] = sname
			}

		}

		for i := 0; i < anzahlZutatenInt; i++ {
			zname := r.FormValue("zutatName" + strconv.Itoa(i+1))
			zmenge, _ := strconv.Atoi(r.FormValue("zutatMenge" + strconv.Itoa(i+1)))
			zeinheit := r.FormValue("zutatEinheit" + strconv.Itoa(i+1))
			idz := r.FormValue("zutatID" + strconv.Itoa(i+1))
			if zname == "" || zmenge == 0 || zeinheit == "" {
				http.Redirect(w, r, ("/rezeptBearbeiten?id=" + id), http.StatusFound)
				return
			} else {
				zutat := model.Zutat{idz, zname, zmenge, zeinheit}
				zutaten[i] = zutat
			}
		}

		bildUp := UploadPicture(r)

		if bildUp == "" {
			bildUp = bild
		}

		rezept := model.Rezept{
			ID:            id,
			Name:          name,
			Art:           art,
			Anlass:        anlass,
			Praeferenz:    praeferenz,
			Kochzeit:      zeit,
			Beschreibung:  beschreibung,
			AnzahlZutaten: anzahlSchritteInt,
			Zutaten:       zutaten,
			Schritte:      schritte,
			Bild:          bildUp,
		}

		temp := model.UpdateRezeptInDB(rezept)
		if temp {
			http.Redirect(w, r, "/alleRezepte", http.StatusFound)
		} else {
			//redirect
		}
	}
}

/*Methode zum Upload eines Bild*/
func UploadPicture(r *http.Request) string {
	if r.Method == "GET" {

	} else {
		r.ParseMultipartForm(32 << 20)
		file, handler, err := r.FormFile("uploadfile")
		if err != nil {
			return ""
		}
		defer file.Close()
		d1, err := os.Create("./static/images/" + handler.Filename)
		defer d1.Close()
		if err != nil {
			return ""
		}
		f, err := os.OpenFile("./static/images/"+handler.Filename, os.O_CREATE, 0666)
		if err != nil {
			return ""
		}
		defer f.Close()
		io.Copy(f, file)
		name := handler.Filename
		return name
	}
	return ""
}
