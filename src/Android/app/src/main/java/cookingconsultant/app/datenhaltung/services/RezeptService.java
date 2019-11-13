package cookingconsultant.app.datenhaltung.services;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;

public interface RezeptService {

    public Rezept getRezeptByID(Integer rezid);
    public List<Rezept> getRezepteByQuery(String query);

}
