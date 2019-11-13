package cookingconsultant.app.fachlogik.services;

import java.util.List;

import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;

public interface EinkaufslisteVerwaltung {

    public EinkaufslisteGrenz getEinkaufslisteByID(Integer einkid);
    public List<EinkaufslisteGrenz> getEinkaufslistenByUserID(Integer userid);
    public boolean deleteEinkaufslisteByID(Integer einkid);
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand);

}
