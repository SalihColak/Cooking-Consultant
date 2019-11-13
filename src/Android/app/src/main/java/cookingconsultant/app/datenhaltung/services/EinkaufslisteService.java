package cookingconsultant.app.datenhaltung.services;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;

public interface EinkaufslisteService {

    public Einkaufsliste getEinkaufslisteByID(Integer einkid);
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid);
    public boolean deleteEinkaufslisteByID(Integer einkid);
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand);
}
