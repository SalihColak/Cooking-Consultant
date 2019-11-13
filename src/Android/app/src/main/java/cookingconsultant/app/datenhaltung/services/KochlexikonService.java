package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Beitrag;

public interface KochlexikonService {

    public Beitrag getBeitragByID(Integer beitid)throws IOException, JSONException;
    public List<Beitrag> getBeitraegeByKategorie(String kategorie)throws IOException, JSONException;

}
