package cookingconsultant.app.fachlogik.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;

public interface KochlexikonVerwaltung {

    public BeitragGrenz getBeitragByID(Integer beitid) throws IOException, JSONException;
    public List<BeitragGrenz> getBeitraegeByKategorie(String kategorie) throws IOException, JSONException;

}
