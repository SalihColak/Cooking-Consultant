package main

import (
	"net/http"
	"syp/end/app/controller"
)

func main() {
	//Registrieren der Handler im Standardrouter für die dynamischen Inhalte
	http.HandleFunc("/alleRezepte", controller.AuthAdmin(controller.AlleRezepte))
	http.HandleFunc("/registerUser", controller.RegisterUser)
	http.HandleFunc("/addUser", controller.AddUser)
	http.HandleFunc("/rezeptEintragen", controller.RezeptEintragen)
	http.HandleFunc("/addRezept", controller.AuthAdmin(controller.AddZerept))
	http.HandleFunc("/rezept", controller.AuthAdmin(controller.GetRezept))
	http.HandleFunc("/", controller.Index)
	http.HandleFunc("/admin", controller.AuthAdmin(controller.IndexAdmin))
	http.HandleFunc("/login", controller.Login)
	http.HandleFunc("/loginUser", controller.LoginUser)
	http.HandleFunc("/logout", controller.Logout)
	http.HandleFunc("/alleUser", controller.AuthAdmin(controller.AlleUser))
	http.HandleFunc("/alleAdmin", controller.AuthAdmin(controller.AlleAdmin))
	http.HandleFunc("/deleteRezept", controller.AuthAdmin(controller.DeleteRezept))
	http.HandleFunc("/deleteUser", controller.AuthAdmin(controller.DeleteUser))
	http.HandleFunc("/beitragEintragen", controller.AuthAdmin(controller.BeitragEintragen))
	http.HandleFunc("/addBeitrag", controller.AuthAdmin(controller.AddBeitrag))
	http.HandleFunc("/alleBeitraege", controller.AuthAdmin(controller.AlleBeitraege))
	http.HandleFunc("/deleteBeitrag", controller.AuthAdmin(controller.DeleteBeitrag))
	http.HandleFunc("/beitrag", controller.AuthAdmin(controller.GetBeitrag))
	http.HandleFunc("/beitragBearbeiten", controller.AuthAdmin(controller.BeitragBearbeiten))
	http.HandleFunc("/updateBeitrag", controller.AuthAdmin(controller.UpdateBeitrag))
	http.HandleFunc("/registerAdmin", controller.AuthAdmin(controller.RegisterAdmin))
	http.HandleFunc("/addAdmin", controller.AuthAdmin(controller.AddAdmin))
	http.HandleFunc("/rezeptBearbeiten", controller.AuthAdmin(controller.UpdateRezept))
	http.HandleFunc("/updateRezept", controller.AuthAdmin(controller.UpdateRezeptDB))
	http.HandleFunc("/userBearbeiten", controller.AuthAdmin(controller.GetUser))
	http.HandleFunc("/user", controller.AuthUser(controller.UserOverview))
	http.HandleFunc("/updateUser", controller.AuthUser(controller.UserUpdate))
	//Registrieren der Handler im Standardrouter für die statischen Inhalte
	http.Handle("/styles/", http.StripPrefix("/styles", http.FileServer(http.Dir("./static/styles"))))
	http.Handle("/script/", http.StripPrefix("/script", http.FileServer(http.Dir("./static/script"))))
	http.Handle("/images/", http.StripPrefix("/images", http.FileServer(http.Dir("./static/images"))))
	//Path des Server
	server := http.Server{
		Addr: ":8080",
	}
	//Starten des Server
	server.ListenAndServe()
}
