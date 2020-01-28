package cookingconsultant.app.fachlogik.impl;


import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.impl.UserServiceImpl;
import cookingconsultant.app.datenhaltung.services.UserService;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.services.UserVerwaltung;

/**
 * UserVerwaltungImpl ist die Systemlogik für die Benutzer der Applikation.
 * Sie greift auf Klassen der Datenhaltung zu.
 *
 * @author Salih Colak
 */
public class UserVerwaltungImpl implements UserVerwaltung {

    private UserService userService;

    public UserVerwaltungImpl(){
        userService = new UserServiceImpl();
    }

    /**
     * Diese Methode liefert den User mit der übergebenen ID.
     *
     * @param userid ID des Users
     * @return UserGrenz-Instanz zu einer äquivalenten User-Instanz aus der DB, falls der User existiert;
     *         null, falls der User mit der ID nicht existiert.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public UserGrenz getUserByID(Integer userid) throws IOException, JSONException {
        User user = userService.getUserByID(userid);
        if(user != null) {
            UserGrenz userGrenz = new UserGrenz(user.getUserid(), user.getTitel(), user.getName(), user.getVorname(),
                    user.getGeschlecht(), user.getGeburtsdatum(), user.isAdmin(), user.getEmail());
            return userGrenz;
        }
        return null;
    }

    /**
     * Diese Methode liefert den User mit zugehöriger E-Mail und Passwort.
     *
     * @param email E-Mail des Users.
     * @param password Passwort des Users.
     * @return UserGrenz-Instanz zu einer äquivalenten User-Instanz aus der DB, falls der User existiert;
     *         null, falls der User mit der übergebenen E-Mail- und Passwortkombination in der DB nicht existiert.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public UserGrenz getUserByLogin(String email, String password) throws IOException, JSONException {
        User user = userService.getUserByLogin(email,password);
        if(user != null) {
            UserGrenz userGrenz = new UserGrenz(user.getUserid(), user.getTitel(), user.getName(), user.getVorname(),
                    user.getGeschlecht(), user.getGeburtsdatum(), user.isAdmin(), user.getEmail());
            return userGrenz;
        }
        return null;
    }
}
