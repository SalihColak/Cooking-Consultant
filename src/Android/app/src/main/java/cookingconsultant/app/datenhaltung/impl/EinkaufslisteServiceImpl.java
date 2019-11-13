package cookingconsultant.app.datenhaltung.impl;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
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
        List<Einkaufsliste> mockList = new ArrayList<>();
        List<Zutat> mockListZutat = new ArrayList<>();
        Zutat milch = new Zutat(3,"Milch","Liter","./bilder/zutaten/milch.png");
        mockListZutat.add(milch);
        mockListZutat.add(new Zutat(5,"Mehl","kg","./bilder/zutaten/mehl.png"));
        Einkaufsliste mock1 = new Einkaufsliste(132,"bearbeitung");
        Einkaufsliste mock2 = new Einkaufsliste(153,"bearbeitung");
        Einkaufsliste mock3 = new Einkaufsliste(111,"bearbeitung");
        mock1.setZutaten(mockListZutat);
        mockListZutat.add(new Zutat(6,"Wasser","Liter","./bilder/zutaten/wasser.png"));
        mock2.setZutaten(mockListZutat);
        mockListZutat.add(new Zutat(7,"Ei","Stueck","./bilder/zutaten/ei.png"));
        mockListZutat.remove(milch);
        mock3.setZutaten(mockListZutat);
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
}
