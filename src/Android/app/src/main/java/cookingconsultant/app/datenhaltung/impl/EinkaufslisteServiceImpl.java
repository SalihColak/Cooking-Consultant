package cookingconsultant.app.datenhaltung.impl;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;

public class EinkaufslisteServiceImpl implements EinkaufslisteService {
    @Override
    public Einkaufsliste getEinkaufslisteByID(Integer einkid) {
        Einkaufsliste einkaufsliste = new Einkaufsliste(einkid,"bearbeitung");
        List<Zutat> mockListZutat = new ArrayList<>();
        mockListZutat.add(new Zutat(3,"Milch","Liter","./bilder/zutaten/milch.png"));
        mockListZutat.add(new Zutat(5,"Mehl","kg","./bilder/zutaten/mehl.png"));
        einkaufsliste.setZutaten(mockListZutat);
        return einkaufsliste;
    }

    @Override
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid) {
        User testUser = new User(1,"Herr","Colak","Salih","m","19/10/1998",true,"s@gmail.com");

        List<Einkaufsliste> mockList = new ArrayList<>();
        List<Zutat> mockListZutat = new ArrayList<>();
        Zutat milch = new Zutat(3,"Milch","Liter","./bilder/zutaten/milch.png");
        mockListZutat.add(milch);
        mockListZutat.add(new Zutat(5,"Mehl","kg","./bilder/zutaten/mehl.png"));
        Einkaufsliste mock1 = new Einkaufsliste(132,"Bearbeitung");
        Einkaufsliste mock2 = new Einkaufsliste(153,"Abgeschlossen");
        Einkaufsliste mock3 = new Einkaufsliste(111,"Bearbeitung");
        mock1.setZutaten(mockListZutat);
        mockListZutat.add(new Zutat(6,"Wasser","Liter","./bilder/zutaten/wasser.png"));
        mock2.setZutaten(mockListZutat);
        mockListZutat.add(new Zutat(7,"Ei","Stueck","./bilder/zutaten/ei.png"));
        mock3.setZutaten(mockListZutat);

        List<String> mockSchritte = new ArrayList<>();
        Rezept rezept = new Rezept(4,"Kokos Torte","Kokos Torten sind lecker.",mockSchritte,"Snack","Party","Amerikanisch","50min","./bilder/rezepte/kokostorte.png");
        mock1.setRezept(rezept);
        mock2.setRezept(rezept);
        mock3.setRezept(rezept);

        mock1.setUser(testUser);
        mock2.setUser(testUser);
        mock3.setUser(testUser);

        mockList.add(mock1);
        mockList.add(mock2);
        mockList.add(mock3);
        return mockList;
    }

    @Override
    public boolean deleteEinkaufslisteByID(Integer einkid) {
        return false;
    }

    @Override
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) {
        Einkaufsliste einkaufsliste = getEinkaufslisteByID(einkid);
        if(einkaufsliste!=null){
            einkaufsliste.setZustand(neuerZustand);
            return true;
        }
        return false;
    }

    @Override
    public boolean addEinkaufsliste(Einkaufsliste einkaufsliste) {
        return true;
    }
}
