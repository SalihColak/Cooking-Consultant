package cookingconsultant.app.fachlogik.services;

import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.fachlogik.grenz.UserGrenz;

public interface UserVerwaltung {

    public UserGrenz getUserByID(Integer userid) throws IOException, JSONException;
    public UserGrenz getUserByLogin(String email, String password) throws IOException, JSONException;

}
