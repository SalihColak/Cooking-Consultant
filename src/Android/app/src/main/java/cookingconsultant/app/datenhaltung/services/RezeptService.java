package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;

public interface RezeptService {

    public Rezept getRezeptByID(Integer rezid)throws IOException, JSONException;
    public List<Rezept> getRezepteByQuery(String query)throws IOException, JSONException;

}
