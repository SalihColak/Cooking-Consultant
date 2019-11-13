package cookingconsultant.app.fachlogik.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;

public interface RezeptVerwaltung {

    public List<RezeptGrenz> getRezeptByQuery(String query) throws IOException, JSONException;
    public RezeptGrenz getRezeptByID(Integer rezid) throws IOException, JSONException;
    public List<ZutatGrenz> getZutatenVonRezeptByID(Integer rezid) throws IOException, JSONException;

}
