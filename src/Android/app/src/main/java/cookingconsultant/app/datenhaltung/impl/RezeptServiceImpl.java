package cookingconsultant.app.datenhaltung.impl;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.services.RezeptService;

public class RezeptServiceImpl implements RezeptService {
    @Override
    public Rezept getRezeptByID(Integer rezid) {
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock = new Rezept(rezid,"Kokos Torte",mockSchritte,"Snack","Party","Amerikanisch","./bilder/rezepte/kokostorte.png");
        return mock;
    }

    @Override
    public List<Rezept> getRezepteByQuery(String query) {
        List<Rezept> mockRezepte = new ArrayList<>();
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock1 = new Rezept(4,"Kokos Torte",mockSchritte,"Snack","Party","Amerikanisch","./bilder/rezepte/kokostorte.png");
        mockSchritte = new ArrayList<>();
        mockSchritte.add("1Liter Milch mit 500gr Zucker einrühren");
        mockSchritte.add("500gr Mehl hinzugügen");
        mockSchritte.add("Eine Packung Backpulver einschütteln.");
        Rezept mock2 = new Rezept(6,"Bananenkuchen",mockSchritte,"Snack","Party","Amerikanisch","./bilder/rezepte/bananenkuchen.png");
        mockSchritte = new ArrayList<>();
        mockSchritte.add("Den Ofen auf 200 Grad vorheizen.");
        mockSchritte.add("Die Zucchini waschen und in etwa 2 cm dicke Scheiben schneiden.");
        mockSchritte.add("Das Backpapier mit Öl einpinseln");
        Rezept mock3 = new Rezept(5,"Zucchini Parmesan Craker",mockSchritte,"Snack","Party","Amerikanisch","./bilder/rezepte/zucchini-cracker.png");
        mockRezepte.add(mock1);
        mockRezepte.add(mock2);
        mockRezepte.add(mock3);
        return mockRezepte;
    }
}
