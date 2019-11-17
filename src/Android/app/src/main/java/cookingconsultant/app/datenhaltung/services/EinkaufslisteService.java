package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;

public interface EinkaufslisteService {

    public Einkaufsliste getEinkaufslisteByID(Integer einkid)throws IOException, JSONException;
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid)throws IOException, JSONException;
    public boolean deleteEinkaufslisteByID(Integer einkid)throws IOException, JSONException;
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand)throws IOException, JSONException;
    public boolean addEinkaufsliste(Einkaufsliste einkaufsliste)throws IOException, JSONException;
}
