package cookingconsultant.app.datenhaltung.services;

import cookingconsultant.app.datenhaltung.entities.User;

public interface UserService {

    public User getUserByID(Integer userid);
    public User getUserByLogin(String email, String password);

}
