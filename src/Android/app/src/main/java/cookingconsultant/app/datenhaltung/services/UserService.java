package cookingconsultant.app.datenhaltung.services;

import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.datenhaltung.entities.User;

public interface UserService {

    public User getUserByID(Integer userid) throws IOException, JSONException;
    public User getUserByLogin(String email, String password) throws IOException, JSONException;

}
