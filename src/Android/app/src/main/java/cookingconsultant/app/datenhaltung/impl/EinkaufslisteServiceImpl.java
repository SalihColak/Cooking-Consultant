package cookingconsultant.app.datenhaltung.impl;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;

public class EinkaufslisteServiceImpl implements EinkaufslisteService {
    @Override
    public Einkaufsliste getEinkaufslisteByID(Integer einkid) {
        return null;
    }

    @Override
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid) {
        return null;
    }

    @Override
    public boolean deleteEinkaufslisteByID(Integer einkid) {
        return false;
    }

    @Override
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) {
        return false;
    }
}
