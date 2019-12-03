package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.ZutatState;

public interface EinkaufslisteService {

    public Einkaufsliste getEinkaufslisteByID(Integer einkid)throws IOException, JSONException;
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid)throws IOException, JSONException;
    public boolean deleteEinkaufslisteByID(Integer einkid)throws IOException, JSONException;
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand)throws IOException, JSONException;
    public boolean addEinkaufsliste(Einkaufsliste einkaufsliste)throws IOException, JSONException;
    public List<ZutatState> getEinkaufslisteZutatState(Integer einkid)throws IOException, JSONException;
    public void insertEinkaufslisteZutatState(Integer einkid, Integer zutid)throws IOException, JSONException;
    public void deleteEinkaufslisteZutatState(Integer einkid)throws IOException, JSONException;
    public void changeZutatState(Integer ein2zutid , boolean state)throws IOException;
}
