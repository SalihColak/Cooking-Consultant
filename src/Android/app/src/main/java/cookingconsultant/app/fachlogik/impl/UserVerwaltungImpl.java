package cookingconsultant.app.fachlogik.impl;


import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.impl.UserServiceImpl;
import cookingconsultant.app.datenhaltung.services.UserService;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.services.UserVerwaltung;

public class UserVerwaltungImpl implements UserVerwaltung {

    private UserService userService;

    public UserVerwaltungImpl(){
        userService = new UserServiceImpl();
    }

    @Override
    public UserGrenz getUserByID(Integer userid) throws IOException, JSONException {
        User user = userService.getUserByID(userid);
        UserGrenz userGrenz = new UserGrenz(user.getUserid(),user.getTitel(),user.getName(),user.getVorname(),
                user.getGeschlecht(),user.getGeburtsdatum(), user.isAdmin(),user.getEmail());
        return userGrenz;
    }

    @Override
    public UserGrenz getUserByLogin(String email, String password) throws IOException, JSONException {
        User user = userService.getUserByLogin(email,password);
        UserGrenz userGrenz = new UserGrenz(user.getUserid(),user.getTitel(),user.getName(),user.getVorname(),
                user.getGeschlecht(),user.getGeburtsdatum(), user.isAdmin(),user.getEmail());
        return userGrenz;
    }
}
