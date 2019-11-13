package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;

public interface ZutatService {

    public Zutat getZutatByID(Integer zutid)throws IOException, JSONException;
    public List<Zutat> getZutatenByRezeptID(Integer rezid)throws IOException, JSONException;

}
