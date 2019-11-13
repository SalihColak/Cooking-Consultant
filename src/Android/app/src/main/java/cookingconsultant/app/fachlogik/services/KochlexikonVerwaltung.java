package cookingconsultant.app.fachlogik.services;

import java.util.List;

import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;

public interface KochlexikonVerwaltung {

    public BeitragGrenz getBeitragByID(Integer beitid);
    public List<BeitragGrenz> getBeitraegeByKategorie(String kategorie);

}
