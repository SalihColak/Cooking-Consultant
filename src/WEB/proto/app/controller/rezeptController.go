package controller

import (
	//"fmt"
	"html/template"
	"net/http"
	"strconv"
	"syp/proto/app/model"
)

type Datenrezept struct {
	Rezept model.Rezept
	Index  []int
}

var tmpl *template.Template

func AlleRezepte(w http.ResponseWriter, r *http.Request) {
	/*r1 := RezeptMin{1, "Ruehrei", "eiq.jpg"}
	r2 := RezeptMin{2, "Bana", "food-placeholder.png"}
	daten := []RezeptMin{r1, r2}*/
	model.LengthRezepte()
	daten := model.GetAlleRezepte()
	var t1 = template.Must(template.ParseFiles("static/template/alleRezepte.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl",
		"static/template/alleRezepteKachel.tmpl"))
	t1.ExecuteTemplate(w, "alleRezepte", daten)

}

func RezeptEintragen(w http.ResponseWriter, r *http.Request) {
	var t1 = template.Must(template.ParseFiles("static/template/addRezept.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl"))
	t1.ExecuteTemplate(w, "addRezept", nil)

}

func AddZerept(w http.ResponseWriter, r *http.Request) {
	name := r.FormValue("name")
	art := r.FormValue("art")
	anlass := r.FormValue("anlass")
	praeferenz := r.FormValue("praeferenz")
	zeit, _ := strconv.Atoi(r.FormValue("zeit"))
	beschreibung := r.FormValue("beschreibung")
	anzahlZutaten := r.FormValue("member")
	anzahlSchritte := r.FormValue("memberText")

	anzahlZutatenInt, _ := strconv.Atoi(anzahlZutaten)
	zutaten := make([]model.Zutat, anzahlZutatenInt)

	anzahlSchritteInt, _ := strconv.Atoi(anzahlSchritte)
	schritte := make([]string, anzahlSchritteInt)

	for i := 0; i < anzahlSchritteInt; i++ {
		sname := r.FormValue("schritt" + strconv.Itoa(i+1))
		schritte[i] = sname

	}

	for i := 0; i < anzahlZutatenInt; i++ {
		zname := r.FormValue("zutatName" + strconv.Itoa(i+1))
		zmenge, _ := strconv.Atoi(r.FormValue("zutatMenge" + strconv.Itoa(i+1)))
		zeinheit := r.FormValue("zutatEinheit" + strconv.Itoa(i+1))
		zutat := model.Zutat{zname, zmenge, zeinheit}
		zutaten[i] = zutat

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
	}

	model.InsertRezept(rezept)
	http.Redirect(w, r, "/alleRezepte", http.StatusFound)

}

func GetRezept(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	daten := model.GetRezeptById(id)

	var index []int
	for i := 0; i < len(daten.Schritte); i++ {
		index = append(index, (i + 1))
	}

	datensatz := Datenrezept{
		Rezept: daten,
		Index:  index,
	}

	var t1 = template.Must(template.ParseFiles("static/template/rezept.tmpl",
		"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl"))
	t1.ExecuteTemplate(w, "rezept", datensatz)
}
