package controller

import (
	"database/sql"
	"net/http"
	"net/http/cookiejar"
	"net/http/httptest"
	"net/url"
	"strings"
	"syp/end/app/model"
	"testing"
)

func BeginnTestUser() {
	testDB, err := sql.Open("mysql", "root:@tcp(127.0.0.1:3306)/sypsave")
	if err != nil {
		panic(err.Error())
	}
	prodDB = model.GetDBCon()
	model.SetDBCon(testDB)
	UserTest(model.GetDBCon())
}

func UserTest(btDB *sql.DB) {
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
}

//TF163
func TestRegisterUser(t *testing.T) {
	router := Router()
	router.HandleFunc("/", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
}

//TF164
func TestAddUser1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AddUser)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
}

//TF165
func TestAddUser2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AddUser)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("anrede", "HERR")
	form.Add("email", "test")
	form.Add("emailW", "test")
	form.Add("passwort", "test")
	form.Add("passwortW", "test")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
}

//TF166
func TestAddUser3(t *testing.T) {
	BeginnTestUser()
	router := Router()
	router.HandleFunc("/", AddUser)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("anrede", "HERR")
	form.Add("email", "test")
	form.Add("emailW", "test")
	form.Add("passwort", "test")
	form.Add("passwortW", "test")
	form.Add("geburtsdatum", "22.02.2006")
	form.Add("name", "test")
	form.Add("vorname", "test")
	form.Add("geschlecht", "m")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
	EndTest()
}

//TF167
func TestIndex(t *testing.T) {
	router := Router()
	router.HandleFunc("/", Index)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
}

//TF168
func TestLogin(t *testing.T) {
	router := Router()
	router.HandleFunc("/", Login)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/login" {
		t.Error("Erwartet /login:", location)
	}
}

//TF169
func TestAlleUser(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AlleUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/alleUser" {
		t.Error("Erwartet /alleUser:", location)
	}
}

//TF170
func TestAlleAdmin(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AlleAdmin)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)
	location := resp.Header.Get("Location")
	if location != "/alleAdmin" {
		t.Error("Erwartet /alleAdmin:", location)
	}
}

//TF171
func TestDeleteUser1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", DeleteUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/admin" {
		t.Error("Erwartet /admin:", location)
	}
}

//TF172
func TestDeleteUser2(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	user2 := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test2",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	model.AddUserToDB(user2)
	router := Router()
	router.HandleFunc("/", DeleteUser)
	router.HandleFunc("/alleAdmin", AlleAdmin)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/admin" {
		t.Error("Erwartet /admin:", location)
	}
	req, _ = http.NewRequest("GET", ts.URL+"/?id=2", nil)
	client = http.Client{}
	resp, _ = client.Do(req)

	location = resp.Header.Get("Location")
	if location != "/admin" {
		t.Error("Erwartet /admin:", location)
	}
	EndTest()
}

//TF173
func TestRegisterAdmin(t *testing.T) {
	router := Router()
	router.HandleFunc("/", RegisterAdmin)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/addAdmin" {
		t.Error("Erwartet /addAdmin:", location)
	}
}

//TF174
func TestAddAdmin1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AddAdmin)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
}

//TF175
func TestAddAdmin2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", AddAdmin)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("anrede", "HERR")
	form.Add("email", "test")
	form.Add("emailW", "test")
	form.Add("passwort", "test")
	form.Add("passwortW", "test")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
}

//TF176
func TestAddAdmin3(t *testing.T) {
	BeginnTestUser()
	router := Router()
	router.HandleFunc("/", AddAdmin)
	router.HandleFunc("/registerUser", RegisterUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("anrede", "HERR")
	form.Add("email", "test")
	form.Add("emailW", "test")
	form.Add("passwort", "test")
	form.Add("passwortW", "test")
	form.Add("geburtsdatum", "22.02.2006")
	form.Add("name", "test")
	form.Add("vorname", "test")
	form.Add("geschlecht", "m")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/registerUser" {
		t.Error("Erwartet /registerUser:", location)
	}
	EndTest()
}

//TF177
func TestGetUser1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleUser" {
		t.Error("Erwartet /alleUser:", location)
	}
}

//TF178
func TestGetUser2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", GetUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/alleUser" {
		t.Error("Erwartet /alleUser:", location)
	}
}

//TF179
func TestGetUser3(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", GetUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/?id=1", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/user" {
		t.Error("Erwartet /user:", location)
	}
	EndTest()
}

//TF180
func TestIndexAdmin(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", IndexAdmin)
	router.HandleFunc("/login", LoginUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("POST", ts.URL+"/", nil)
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/admin" {
		t.Error("Erwartet /admin:", location)
	}
	EndTest()
}

//TF181
func TestAuthUser1(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", AuthUser(Index))
	router.HandleFunc("/login", LoginUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("POST", ts.URL+"/", nil)
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
	EndTest()
}

//TF182
func TestAuthUser2(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", AuthUser(Index))
	router.HandleFunc("/login", LoginUser)
	router.HandleFunc("/logout", Logout)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("GET", ts.URL+"/logout", nil)
	resp, _ = client.Do(req)
	req, _ = http.NewRequest("GET", ts.URL+"/", nil)
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
	EndTest()
}

//TF183
func TestAuthAdmin1(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", AuthAdmin(Index))
	router.HandleFunc("/login", LoginUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("POST", ts.URL+"/", nil)
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
	EndTest()
}

//TF184
func TestAuthAdmin2(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", AuthAdmin(Index))
	router.HandleFunc("/login", LoginUser)
	router.HandleFunc("/logout", Logout)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("GET", ts.URL+"/logout", nil)
	resp, _ = client.Do(req)
	req, _ = http.NewRequest("GET", ts.URL+"/", nil)
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
	EndTest()
}

//TF185
func TestUserUpdate1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UserUpdate)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("POST", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
}

//TF186
func TestUserUpdate2(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", UserUpdate)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("id", "1")
	req, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != ("/user?id=1") {
		t.Error("Erwartet /user?id=1", location)
	}
	EndTest()
}

//TF187
func TestUserUpdate3(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", UserUpdate)
	router.HandleFunc("/login", Login)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("id", "1")
	form.Add("name", "Test2")
	form.Add("vorname", "Test2")
	form.Add("email", "Test2")
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req, _ = http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	resp, _ = client.Do(req)

	location := resp.Header.Get("Location")
	if location != ("/user?id=1") {
		t.Error("Erwartet /user?id=1", location)
	}
	EndTest()
}

//TF188
func TestUserUpdate4(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "1",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", UserUpdate)
	router.HandleFunc("/login", LoginUser)
	router.HandleFunc("/admin", IndexAdmin)
	router.HandleFunc("/userBearbeiten", GetUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	form := url.Values{}
	form.Add("id", "1")
	form.Add("name", "Test2")
	form.Add("vorname", "Test2")
	form.Add("email", "test")
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req2, _ := http.NewRequest("POST", ts.URL+"/", strings.NewReader(form.Encode()))
	req2.Header.Add("Content-Type", "application/x-www-form-urlencoded")
	resp, _ = client.Do(req2)

	location := resp.Header.Get("Location")
	if location != ("/user") {
		t.Error("Erwartet /user", location)
	}
	EndTest()
}

//TF189
func TestUserOverview1(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UserOverview)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
}

//TF190
func TestUserOverview2(t *testing.T) {
	router := Router()
	router.HandleFunc("/", UserOverview)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/?id=0", nil)
	client := http.Client{}
	resp, _ := client.Do(req)

	location := resp.Header.Get("Location")
	if location != "/" {
		t.Error("Erwartet /:", location)
	}
}

//TF191
func TestUserOverview3(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", UserOverview)
	router.HandleFunc("/login", LoginUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req2, _ := http.NewRequest("POST", ts.URL+"/?id=1", nil)
	resp, _ = client.Do(req2)

	location := resp.Header.Get("Location")
	if location != ("/user") {
		t.Error("Erwartet /user", location)
	}
	EndTest()
}

//TF192
func TestUserOverview4(t *testing.T) {
	BeginnTestUser()
	user := model.User{
		Titel:        "Herr",
		Name:         "Test",
		Vorname:      "Test",
		Geschlecht:   "m",
		Geburtsdatum: "2006-02-22",
		Email:        "test",
		Passwort:     "test",
		Admin:        "0",
	}
	model.AddUserToDB(user)
	router := Router()
	router.HandleFunc("/", UserOverview)
	router.HandleFunc("/login", LoginUser)
	ts := httptest.NewServer(router)
	defer ts.Close()
	req, _ := http.NewRequest("GET", ts.URL+"/login?email=test&passwort=test", nil)
	jar, _ := cookiejar.New(nil)
	client := http.Client{Jar: jar}
	resp, _ := client.Do(req)
	req2, _ := http.NewRequest("POST", ts.URL+"/", nil)
	resp, _ = client.Do(req2)

	location := resp.Header.Get("Location")
	if location != ("/") {
		t.Error("Erwartet /", location)
	}
	EndTest()
}
