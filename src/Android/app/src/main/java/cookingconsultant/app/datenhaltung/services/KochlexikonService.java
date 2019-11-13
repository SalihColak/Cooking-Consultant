package cookingconsultant.app.datenhaltung.services;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Beitrag;

public interface KochlexikonService {

    public Beitrag getBeitragByID(Integer beitid);
    public List<Beitrag> getBeitraegeByKategorie(String kategorie);

}
