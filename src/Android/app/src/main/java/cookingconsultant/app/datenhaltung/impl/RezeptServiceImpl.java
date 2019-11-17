package cookingconsultant.app.datenhaltung.impl;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;

public class RezeptServiceImpl implements RezeptService {
    @Override
    public Rezept getRezeptByID(Integer rezid) {
        List<Zutat> zutatList = new ArrayList<>();
        zutatList.add(new Zutat(3,"Wasser","Liter",""));
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock = new Rezept(rezid,"Kokos Torte","Kokos Torten sind einfach der Hammer.",mockSchritte,"Snack","Party","Amerikanisch","20min","./bilder/rezepte/kokostorte.png");
        mock.setZutaten(zutatList);
        return mock;
    }

    @Override
    public List<Rezept> getRezepteByQuery(String query) {

        List<Zutat> zutatList = new ArrayList<>();
        zutatList.add(new Zutat(3,"Wasser","Liter",""));

        List<Rezept> mockRezepte = new ArrayList<>();
        List<String> mockSchritte = new ArrayList<>();
        mockSchritte.add("Hände waschen");
        mockSchritte.add("2 Eier mit 500gr Mehl und 1L Milch umrühren");
        mockSchritte.add("500mL Kokos-Milch einrühren");
        Rezept mock1 = new Rezept(4,"Kokos Torte","Kokos Torten sind lecker.",mockSchritte,"Snack","Party","Amerikanisch","50min","./bilder/rezepte/kokostorte.png");
        mock1.setZutaten(zutatList);
        mockSchritte = new ArrayList<>();
        mockSchritte.add("1Liter Milch mit 500gr Zucker einrühren");
        mockSchritte.add("500gr Mehl hinzugügen");
        mockSchritte.add("Eine Packung Backpulver einschütteln.");
        Rezept mock2 = new Rezept(6,"Bananenkuchen","Bananenkuchen sind einfach der Hammer",mockSchritte,"Snack","Party","Amerikanisch","20min","./bilder/rezepte/bananenkuchen.png");
        mock2.setZutaten(zutatList);
        mockSchritte = new ArrayList<>();
        mockSchritte.add("Den Ofen auf 200 Grad vorheizen.");
        mockSchritte.add("Die Zucchini waschen und in etwa 2 cm dicke Scheiben schneiden.");
        mockSchritte.add("Das Backpapier mit Öl einpinseln");
        Rezept mock3 = new Rezept(5,"Zucchini Parmesan Craker","Immer wieder gern.",mockSchritte,"Snack","Party","Amerikanisch","55min","./bilder/rezepte/zucchini-cracker.png");
        mock3.setZutaten(zutatList);
        mockRezepte.add(mock1);
        mockRezepte.add(mock2);
        mockRezepte.add(mock3);
        return mockRezepte;
    }
}
