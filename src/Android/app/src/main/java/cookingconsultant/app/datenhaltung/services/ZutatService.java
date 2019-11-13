package cookingconsultant.app.datenhaltung.services;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;

public interface ZutatService {

    public Zutat getZutatByID(Integer zutid);
    public List<Zutat> getZutatenByRezeptID(Integer rezid);

}
