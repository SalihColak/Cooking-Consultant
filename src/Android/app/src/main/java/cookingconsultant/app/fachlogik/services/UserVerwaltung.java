package cookingconsultant.app.fachlogik.services;

import cookingconsultant.app.fachlogik.grenz.UserGrenz;

public interface UserVerwaltung {

    public UserGrenz getUserByID(Integer userid);
    public UserGrenz getUserByLogin(String email, String password);

}
