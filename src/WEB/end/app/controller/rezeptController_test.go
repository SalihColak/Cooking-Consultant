package controller

import (
	"database/sql"
	"net/http"
	"net/http/httptest"
	"net/url"
	"strings"
	"syp/end/app/model"
	"testing"
)

var prodDB *sql.DB

func BeginnTest() {
	testDB, err := sql.Open("mysql", "root:@tcp(127.0.0.1:3306)/sypsave")
	if err != nil {
		panic(err.Error())
	}
	prodDB = model.GetDBCon()
	model.SetDBCon(testDB)
	RezeptTest(model.GetDBCon())
}

func RezeptTest(btDB *sql.DB) {
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
}

func EndTest() {
	model.SetDBCon(prodDB)
}

func Router() *http.ServeMux {
	mux := http.NewServeMux()
	return mux
}

//Keine Angabe der ID
//TF145
func TestGetRezept1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//Nicht existierente ID
//TF146
func TestGetRezept2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//Existierende ID
//TF147
func TestGetRezept3(t *testing.T) {
	BeginnTest()

	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", GetRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/rezept?id=1" {
		t.Error("Erwartet /rezept?id=1:", location)
	}
	EndTest()
}

//TF148
func TestRezeptEintragen(t *testing.T) {
	router := Router()
	router.HandleFunc("/", RezeptEintragen)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/rezeptEintragen" {
		t.Error("Erwartet /rezeptEintragen:", location)
	}
}

//TF149
func TestAlleRezepte(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AlleRezepte)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//TF150
//Keine Formulardaten
func TestAddZerept1(t *testing.T) {
	BeginnTest()
	router := Router()
	router.HandleFunc("/", AddZerept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/addRezept" {
		t.Error("Erwartet /addRezept:", location)
	}
	EndTest()
}

//TF151
//Formulardaten Schritte und Zutaten erzeugt
func TestAddZerept2(t *testing.T) {
	BeginnTest()
	router := Router()
	router.HandleFunc("/", AddZerept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/addRezept" {
		t.Error("Erwartet /addRezept:", location)
	}
	EndTest()
}

//TF152
//Formulardaten mit Schritt
func TestAddZerept3(t *testing.T) {
	BeginnTest()
	router := Router()
	router.HandleFunc("/", AddZerept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	form.Add("schritt1", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/addRezept" {
		t.Error("Erwartet /addRezept:", location)
	}
	EndTest()
}

//TF153
//Alle Formulardaten ok
func TestAddZerept4(t *testing.T) {
	BeginnTest()
	router := Router()
	router.HandleFunc("/", AddZerept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	form.Add("schritt1", "1")
	form.Add("zutatName1", "Wasser")
	form.Add("zutatMenge1", "100")
	form.Add("zutatEinheit1", "ml")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/addRezept" {
		t.Error("Erwartet /addRezept:", location)
	}
	EndTest()

}

//TF154
//keine Angabe der ID
func TestDeleteRezept1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", DeleteRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//TF155
//Angabe einer existierenden ID
func TestDeleteRezept2(t *testing.T) {
	BeginnTest()

	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", DeleteRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
	EndTest()
}

//TF156
//Keine ID angegeben
func TestUpdateRezept1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//TF157
//Nicht existierende ID angegeben
func TestUpdateRezept2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//TF158
//Existierende ID angegeben
func TestUpdateRezept3(t *testing.T) {
	BeginnTest()

	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/rezeptBearbeiten?id=1" {
		t.Error("Erwartet /rezeptBearbeiten?id=1", location)
	}
	EndTest()
}

//TF159
////Keine Formulardaten
func TestUpdateRezeptDB1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UpdateRezeptDB)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
}

//TF160
//Keine Formulardaten
func TestUpdateRezeptDB2(t *testing.T) {
	BeginnTest()
	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", UpdateRezeptDB)
	router.HandleFunc("/rezeptBearbeiten", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	form.Add("id", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/rezeptBearbeiten?id=1" {
		t.Error("Erwartet /rezeptBearbeiten?id=1:", location)
	}
	EndTest()

}

//TF161
func TestUpdateRezeptDB3(t *testing.T) {
	BeginnTest()
	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", UpdateRezeptDB)
	router.HandleFunc("/rezeptBearbeiten", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	form.Add("id", "1")
	form.Add("schritt1", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/rezeptBearbeiten?id=1" {
		t.Error("Erwartet /rezeptBearbeiten?id=1:", location)
	}
	EndTest()

}

//TF162
func TestUpdateRezeptDB4(t *testing.T) {
	BeginnTest()
	zutat := []model.Zutat{model.Zutat{Name: "Test1", Einheit: "ML", Menge: 100}}
	schritte := []string{"Test"}
	rezept := model.Rezept{
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
	model.InsertRezept(rezept)
	router := Router()
	router.HandleFunc("/", UpdateRezeptDB)
	router.HandleFunc("/rezeptBearbeiten", UpdateRezept)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("name", "Test2")
	form.Add("art", "BRUNCH")
	form.Add("anlass", "FREUNDE")
	form.Add("praeferenz", "DEUTSCH")
	form.Add("beschreibung", "Test2")
	form.Add("member", "1")
	form.Add("memberText", "1")
	form.Add("id", "1")
	form.Add("schritt1", "1")
	form.Add("zutatName1", "Wasser")
	form.Add("zutatMenge1", "100")
	form.Add("zutatEinheit1", "ml")
	form.Add("zeit", "20")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleRezepte" {
		t.Error("Erwartet /alleRezepte:", location)
	}
	EndTest()

}
