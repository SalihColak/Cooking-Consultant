package main

import (
	"net/http"
	"syp/proto/app/controller"
)

func main() {

	http.HandleFunc("/alleRezepte", controller.AlleRezepte)
	http.HandleFunc("/login", controller.LoginUser)
	http.HandleFunc("/addUser", controller.AddUser)
	http.HandleFunc("/rezeptEintragen", controller.RezeptEintragen)
	http.HandleFunc("/addRezept", controller.AddZerept)
	http.HandleFunc("/rezept", controller.GetRezept)

	http.Handle("/styles/", http.StripPrefix("/styles", http.FileServer(http.Dir("./static/styles"))))
	http.Handle("/script/", http.StripPrefix("/script", http.FileServer(http.Dir("./static/script"))))
	http.Handle("/images/", http.StripPrefix("/images", http.FileServer(http.Dir("./static/images"))))

	server := http.Server{
		Addr: ":8080",
	}

	server.ListenAndServe()
}
