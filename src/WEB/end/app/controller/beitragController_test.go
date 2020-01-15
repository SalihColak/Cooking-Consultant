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

func BeginnTestBeitrag() {
	testDB, err := sql.Open("mysql", "root:@tcp(127.0.0.1:3306)/sypsave")
	if err != nil {
		panic(err.Error())
	}
	prodDB = model.GetDBCon()
	model.SetDBCon(testDB)
	BeitragTest(model.GetDBCon())
}

func BeitragTest(btDB *sql.DB) {
	trans, _ := btDB.Begin()
	_, err := trans.Exec("DELETE FROM `beitrag`")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	_, err = trans.Exec("ALTER TABLE beitrag AUTO_INCREMENT = 1")
	if err != nil {
		println(err.Error())
		trans.Rollback()
	}
	trans.Commit()
}

//TF130
func TestBeitragEintragen(t *testing.T) {
	router := Router()
	router.HandleFunc("/", BeitragEintragen)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/beitragEintragen" {
		t.Error("Erwartet /beitragEintragen:", location)
	}
}

//TF131
func TestAddBeitrag1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AddBeitrag)
	router.HandleFunc("/beitragEintragen", BeitragEintragen)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/beitragEintragen" {
		t.Error("Erwartet /beitragEintragen:", location)
	}

}

//TF132
func TestAddBeitrag2(t *testing.T) {
	BeginnTestBeitrag()
	router := Router()
	router.HandleFunc("/", AddBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("titel", "Test2")
	form.Add("kategorie", "Lebensmittel")
	form.Add("inhalt", "Test2")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
	EndTest()

}

//TF133
func TestAlleBeitraege(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF134
func TestDeleteBeitrag1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", DeleteBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF135
func TestDeleteBeitrag2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", DeleteBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF136
func TestDeleteBeitrag3(t *testing.T) {
	BeginnTestBeitrag()
	beitrag := model.Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	model.AddBeitragToDB(beitrag)
	router := Router()
	router.HandleFunc("/", DeleteBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
	EndTest()
}

//TF137
func TestGetBeitrag1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF138
func TestGetBeitrag2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF139
func TestGetBeitrag3(t *testing.T) {
	BeginnTestBeitrag()
	beitrag := model.Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	model.AddBeitragToDB(beitrag)
	router := Router()
	router.HandleFunc("/", GetBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/beitrag" {
		t.Error("Erwartet /beitrag:", location)
	}
	EndTest()
}

//TF140
func TestBeitragBearbeiten1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", BeitragBearbeiten)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF141
func TestBeitragBearbeiten2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", BeitragBearbeiten)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF142
func TestBeitragBearbeiten3(t *testing.T) {
	BeginnTestBeitrag()
	beitrag := model.Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	model.AddBeitragToDB(beitrag)
	router := Router()
	router.HandleFunc("/", BeitragBearbeiten)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/beitragBearbeiten?id=1" {
		t.Error("Erwartet /beitragBearbeiten?id=1 :", location)
	}
	EndTest()
}

//TF143
func TestUpdateBeitrag1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UpdateBeitrag)
	router.HandleFunc("/alleBeitraege", AlleBeitraege)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
}

//TF144
func TestUpdateBeitrag2(t *testing.T) {
	BeginnTestBeitrag()
	beitrag := model.Beitrag{
		Inhalt:    "Test",
		Kategorie: "Lebensmittel",
		Titel:     "Test",
	}
	model.AddBeitragToDB(beitrag)
	router := Router()
	router.HandleFunc("/", UpdateBeitrag)
	router.HandleFunc("/beitragBearbeiten", BeitragBearbeiten)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("titel", "Test")
	form.Add("kategorie", "Lebensmittel")
	form.Add("inhalt", "Test")
	form.Add("id", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/beitragBearbeiten?id=1" {
		t.Error("Erwartet /beitragBearbeiten?id=1:", location)
	}

	form = url.Values{}
	form.Add("titel", "Test2")
	form.Add("kategorie", "Lebensmittel")
	form.Add("inhalt", "Test2")
	form.Add("id", "1")
	req, _ = http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client = http.Client{}
	resp, _ = client.Do(req)

	location = resp.Header.Get("Location")
	if location != "/alleBeitraege" {
		t.Error("Erwartet /alleBeitraege:", location)
	}
	EndTest()
}
