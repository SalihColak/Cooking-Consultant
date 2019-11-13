package cookingconsultant.app.fachlogik.services;

import java.util.List;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;

public interface RezeptVerwaltung {

    public List<RezeptGrenz> getRezeptByQuery(String query);
    public RezeptGrenz getRezeptByID(Integer rezid);
    public List<ZutatGrenz> getZutatenVonRezeptByID(Integer rezid);

}
