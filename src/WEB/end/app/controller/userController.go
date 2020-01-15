package controller

import (
	"html/template"
	"math/rand"
	"net/http"
	"strconv"
	"syp/end/app/model"
	"time"

	"github.com/gorilla/sessions"
)

/*DatenAdmin Struct für die Daten, welche dem Admin auf der Startseite angezeigt werden*/
type DatenAdmin struct {
	Vorname   string
	Nachname  string
	Rezept    int
	Beitrag   int
	User      int
	Rezepte   []string
	Beitraege []string
}

var store *sessions.CookieStore

func init() {
	key := make([]byte, 32)
	rand.Read(key)
	store = sessions.NewCookieStore(key)
}

/*RegisterUser Methode zum anzeigen einer Registermaske*/
func RegisterUser(w http.ResponseWriter, r *http.Request) {
	temp, err := template.ParseFiles("static/template/addUser.tmpl")
	if err != nil {
		w.Header().Set("Location", "/registerUser")
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
	t1.ExecuteTemplate(w, "addUser", nil)
}

/*AddUser Methode zum erzeugen eines neuen Benutzer*/
func AddUser(w http.ResponseWriter, r *http.Request) {
	anrede := r.FormValue("anrede")
	email := r.FormValue("email")
	emailW := r.FormValue("emailW")
	passwort := r.FormValue("passwort")
	passwortW := r.FormValue("passwortW")
	if passwort != passwortW || email != emailW || passwort == "" || email == "" {
		http.Redirect(w, r, "/registerUser", http.StatusFound)
		println(1)
	} else {

		geburtsdatumWeb := r.FormValue("geburtsdatum")
		name := r.FormValue("name")
		vorname := r.FormValue("vorname")
		geschlecht := r.FormValue("geschlecht")

		if name == "" || vorname == "" || geburtsdatumWeb == "" || anrede == "" || geschlecht == "" {
			http.Redirect(w, r, "/registerUser", http.StatusFound)
			println(2)
		} else {

			temp, _ := time.Parse("02.01.2006", geburtsdatumWeb)
			geburtsdatum := temp.String()

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
			id := model.AddUserToDB(nutzer)

			if id != 0 {
				http.Redirect(w, r, "/loginUser?email="+email+"&passwort="+passwort, http.StatusFound)
			} else {
				//redirect
			}
		}
	}
}

/*Index Methode zum Anzeigen einer Stzartseite für den Benutzer*/
func Index(w http.ResponseWriter, r *http.Request) {
	temp, err := template.ParseFiles("static/template/index.tmpl")
	if err != nil {
		w.Header().Set("Location", "/")
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
	t1.ExecuteTemplate(w, "index", nil)
}

/*IndexAdmin Methode zum Anzeigen einer Stzartseite für den Admin*/
func IndexAdmin(w http.ResponseWriter, r *http.Request) {
	anzRezepte := model.LengthRezepte()
	anzUser := model.LengthUser()
	anzBeitrag := model.LengthBeitrag()
	session, _ := store.Get(r, "session")
	email := session.Values["username"].(string)
	user := model.GetUser(email)
	rezepte := model.LastRezepte()
	beitraege := model.LastBeitrag()
	daten := DatenAdmin{
		Vorname:   user.Vorname,
		Nachname:  user.Name,
		Rezept:    anzRezepte,
		Beitrag:   anzBeitrag,
		User:      anzUser,
		Rezepte:   rezepte,
		Beitraege: beitraege,
	}

	temp, err := template.ParseFiles("static/template/indexAdmin.tmpl", "static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
	if err != nil {
		w.Header().Set("Location", "/admin")
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
	t1.ExecuteTemplate(w, "indexAdmin", daten)
}

/*Login Methode zur anzeige einer Anmeldung eines Admins/Benutzers*/
func Login(w http.ResponseWriter, r *http.Request) {
	temp, err := template.ParseFiles("static/template/login.tmpl")
	if err != nil {
		w.Header().Set("Location", "/login")
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
	t1.ExecuteTemplate(w, "login", nil)
}

/*LoginUser Methode zur Anmeldung eines Admins/Benutzers*/
func LoginUser(w http.ResponseWriter, r *http.Request) {
	email := r.FormValue("email")
	passwort := r.FormValue("passwort")
	if passwort == "" || email == "" {
		//redirect
	} else {
		user := model.GetUser(email)
		if user.UseID == 0 {
			http.Redirect(w, r, "/login", http.StatusFound)
		} else {
			if passwort == user.Passwort {
				if user.Admin == "1" {
					session, _ := store.Get(r, "session")
					session.Values["authenticated"] = true
					session.Values["username"] = email
					session.Values["admin"] = true
					session.Save(r, w)
					http.Redirect(w, r, "/admin", http.StatusFound)
				} else {
					session, _ := store.Get(r, "session")
					session.Values["authenticated"] = true
					session.Values["username"] = email
					session.Values["admin"] = false
					session.Save(r, w)
					user := strconv.Itoa(model.GetUser(email).UseID)
					http.Redirect(w, r, "/user?id="+user, http.StatusFound)
				}
			}
		}
	}
}

/*AuthUser Methode zur authenfizierung eines Benutzers*/
func AuthUser(h http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		session, _ := store.Get(r, "session")

		// Check if user is authenticated
		if auth, ok := session.Values["authenticated"].(bool); !ok || !auth {
			http.Redirect(w, r, "/", http.StatusFound)
		} else {
			h(w, r)
		}
	}
}

/*AuthAdmin Methode zur authenfizierung eines Admins*/
func AuthAdmin(h http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		session, _ := store.Get(r, "session")
		auth, ok := session.Values["authenticated"].(bool)
		admin, ok2 := session.Values["admin"].(bool)
		// Check if user is authenticated
		if !ok || !auth || !admin || !ok2 {
			http.Redirect(w, r, "/", http.StatusFound)
		} else {
			h(w, r)
		}
	}
}

/*Logout Methode zum Logout eines Admins/Benutzer*/
func Logout(w http.ResponseWriter, r *http.Request) {
	session, _ := store.Get(r, "session")

	session.Values["authenticated"] = false
	session.Values["email"] = ""
	session.Values["admin"] = false
	session.Save(r, w)

	http.Redirect(w, r, "/", http.StatusFound)
}

/*AlleUser Methode zum anzeigen aller Benutzer*/
func AlleUser(w http.ResponseWriter, r *http.Request) {
	daten := model.GetAlleUser()
	if daten == nil {
		http.Redirect(w, r, "/admin", http.StatusFound)
	} else {
		temp, err := template.ParseFiles("static/template/alleUser.tmpl",
			"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
		if err != nil {
			w.Header().Set("Location", "/alleUser")
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
		t1.ExecuteTemplate(w, "alleUser", daten)
	}
}

/*AlleAdmin Methode zum anzeigen aller Admins*/
func AlleAdmin(w http.ResponseWriter, r *http.Request) {
	daten := model.GetAlleAdmin()
	if daten == nil {
		http.Redirect(w, r, "/admin", http.StatusFound)
	} else {
		temp, err := template.ParseFiles("static/template/alleUser.tmpl",
			"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
		if err != nil {
			w.Header().Set("Location", "/alleAdmin")
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
		t1.ExecuteTemplate(w, "alleUser", daten)
	}
}

/*Notification Methode zum erstellen einer Push Nachricht*/
//func Notification(w http.ResponseWriter, r *http.Request) {
//	var t1 = template.Must(template.ParseFiles("static/template/test.tmpl"))
//	t1.ExecuteTemplate(w, "test", nil)
//}

/*Push Methode zum senden einer Push Nachricht*/
//func Push(w http.ResponseWriter, r *http.Request) {
//	title := r.FormValue("title")
//	body := r.FormValue("body")
//
//	model.SendPush(title, body)
//}

/*DeleteUser Methode zum löschen eines Benutzer*/
func DeleteUser(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/admin", http.StatusFound)
	} else {
		user := model.GetAdminState(id)
		temp := model.DeleteUser(id)

		if temp {

			if user == "1" {
				http.Redirect(w, r, "/alleAdmin", http.StatusFound)
			} else {
				http.Redirect(w, r, "/alleUser", http.StatusFound)
			}
		} else {
			http.Redirect(w, r, "/admin", http.StatusFound)
		}
	}
}

/*RegisterAdmin Methode zum eigeben der Daten eines neuen Admin*/
func RegisterAdmin(w http.ResponseWriter, r *http.Request) {
	temp, err := template.ParseFiles("static/template/addAdmin.tmpl", "static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
	if err != nil {
		w.Header().Set("Location", "/addAdmin")
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
	t1.ExecuteTemplate(w, "addAdmin", nil)
}

/*AddAdmin Methode zum erzeugen eines neuen Admin*/
func AddAdmin(w http.ResponseWriter, r *http.Request) {
	anrede := r.FormValue("anrede")
	email := r.FormValue("email")
	emailW := r.FormValue("emailW")
	passwort := r.FormValue("passwort")
	passwortW := r.FormValue("passwortW")
	if passwort != passwortW || email != emailW || passwort == "" || email == "" {
		http.Redirect(w, r, "/registerUser", http.StatusFound)
	} else {

		geburtsdatumWeb := r.FormValue("geburtsdatum")
		name := r.FormValue("name")
		vorname := r.FormValue("vorname")
		geschlecht := r.FormValue("geschlecht")

		if name == "" || vorname == "" || geburtsdatumWeb == "" || anrede == "" || geschlecht == "" {
			http.Redirect(w, r, "/registerUser", http.StatusFound)
		} else {

			temp, _ := time.Parse("02.01.2006", geburtsdatumWeb)
			geburtsdatum := temp.String()

			nutzer := model.User{
				Titel:        anrede,
				Vorname:      vorname,
				Name:         name,
				Geschlecht:   geschlecht,
				Geburtsdatum: geburtsdatum,
				Email:        email,
				Passwort:     passwort,
				Admin:        "1",
			}
			id := model.AddUserToDB(nutzer)

			if id != 0 {
				http.Redirect(w, r, "/loginUser?email="+email+"&passwort="+passwort, http.StatusFound)
			} else {
				//redirect
			}
		}
	}
}

/*GetUser Methode zum Anzeigen des Benutzers bei dem Admin*/
func GetUser(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/alleUser", http.StatusFound)
	} else {
		daten := model.GetUserByID(id)
		if daten.UseID == 0 {
			http.Redirect(w, r, "/alleUser", http.StatusFound)
		} else {
			temp, err := template.ParseFiles("static/template/user.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", "/user")
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
			t1.ExecuteTemplate(w, "user", daten)
		}
	}
}

/*UserOverview Methode zum Anzeigen des Benutzers bei dem Benutzer*/
func UserOverview(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	if id == "" {
		http.Redirect(w, r, "/", http.StatusFound)
	} else {
		session, _ := store.Get(r, "session")
		admin, _ := session.Values["admin"].(bool)
		daten := model.GetUserByID(id)
		if daten.UseID == 0 {
			http.Redirect(w, r, "/", http.StatusFound)
		} else {
			if admin == false {
				user := session.Values["username"].(string)
				if user != daten.Email {
					realUser := model.GetUser(user)
					http.Redirect(w, r, "/user?id="+strconv.Itoa(realUser.UseID), http.StatusFound)
				}
			}
			temp, err := template.ParseFiles("static/template/useroverview.tmpl",
				"static/template/headAdmin.tmpl", "static/template/headerAdmin.tmpl")
			if err != nil {
				w.Header().Set("Location", "/user")
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
			t1.ExecuteTemplate(w, "useroverview", daten)
		}
	}
}

/*UserUpdate Methode zum Updaten des Benutzers senden*/
func UserUpdate(w http.ResponseWriter, r *http.Request) {
	id := r.FormValue("id")
	name := r.FormValue("name")
	vorname := r.FormValue("vorname")
	email := r.FormValue("email")
	passwort := r.FormValue("passwort")
	user := model.GetUserByID(id)
	if user.UseID == 0 {
		http.Redirect(w, r, "/", http.StatusFound)
	} else {
		if passwort != "" {
			user.Passwort = passwort
		}
		if name == "" || vorname == "" || email == "" || user.Passwort == "" {
			http.Redirect(w, r, "/user?id="+id, http.StatusFound)
		} else {
			user.Name = name
			user.Vorname = vorname
			user.Email = email
			temp := model.UpdateUserToDB(user)
			if temp {
				session, _ := store.Get(r, "session")
				admin, _ := session.Values["admin"].(bool)
				if admin {
					http.Redirect(w, r, "/userBearbeiten?id="+strconv.Itoa(user.UseID), http.StatusFound)
				} else {
					session.Values["username"] = email
					session.Save(r, w)
					http.Redirect(w, r, "/user?id="+id, http.StatusFound)
				}
			} else {
				//redirect
			}
		}
	}
}
