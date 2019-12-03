package cookingconsultant.app.fachlogik.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;

public interface EinkaufslisteVerwaltung {

    public EinkaufslisteGrenz getEinkaufslisteByID(Integer einkid) throws IOException, JSONException;
    public List<EinkaufslisteGrenz> getEinkaufslistenByUserID(Integer userid) throws IOException, JSONException;
    public boolean deleteEinkaufslisteByID(Integer einkid) throws IOException, JSONException;
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) throws IOException, JSONException;
    public boolean addEinkaufsliste(EinkaufslisteGrenz einkaufslisteGrenz) throws IOException, JSONException;
    public void changeZutatState(Integer ein2zutid, boolean state)throws IOException;

}
