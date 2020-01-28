package cookingconsultant.app.fachlogik.impl;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.impl.RezeptServiceImpl;
import cookingconsultant.app.datenhaltung.impl.ZutatServiceImpl;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.ZutatService;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;

/**
 * RezeptVerwaltungImpl ist die Systemlogik für die Rezepte der Applikation.
 * Sie greift auf Klassen der Datenhaltung zu.
 *
 * @author Salih Colak
 */
public class RezeptVerwaltungImpl implements RezeptVerwaltung {

    private RezeptService rezeptService;
    private ZutatService zutatService;

    public RezeptVerwaltungImpl(){
        zutatService = new ZutatServiceImpl();
        rezeptService = new RezeptServiceImpl();
    }

    /**
     * Diese Methode liefert eine Liste von RezeptGrenz-Instanzen die nach Art, Anlass, und Kategorie gefiltert sind.
     *
     * @param query Filter-String für Rezeptsuche.
     * @return Liste von RezeptGrenz-Instanzen.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public List<RezeptGrenz> getRezeptByQuery(String query) throws IOException, JSONException {
        List<RezeptGrenz> rezeptGrenzList = new ArrayList<>();
        List<Rezept> rezeptList = rezeptService.getRezepteByQuery(query);
        if(rezeptList!=null){
            for (Rezept rezept : rezeptList){
                rezeptGrenzList.add(new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getBeschreibung(),rezept.getSchritte(),
                        rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getKochzeit(),rezept.getBild(),rezept.getMenge()));
            }
        }
        return rezeptGrenzList;
    }

    /**
     * Diese Methode sucht nach einem Rezept in der DB und liefert eine äquivalente RezeptGrenz-Instanz.
     *
     * @param rezid ID des Rezeptes.
     * @return RezeptGrenz-Instanz, falls das Rezept in der DB existiert;
     *         null, falls das Rezept in der DB nicht existiert.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public RezeptGrenz getRezeptByID(Integer rezid) throws IOException, JSONException {
        Rezept rezept = rezeptService.getRezeptByID(rezid);
        if(rezept != null){
            List<Zutat> zutatList = rezept.getZutaten();
            List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
            for(Zutat zutat : zutatList){
                zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
            }
            RezeptGrenz rezeptGrenz = new  RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getBeschreibung(),rezept.getSchritte(),
                    rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getKochzeit(),rezept.getBild(),rezept.getMenge());
            rezeptGrenz.setZutaten(zutatGrenzList);
            return rezeptGrenz;
        }
        return null;
    }

    /**
     * Liefert eine Liste von ZutatGrenz-Instanzen für für ein Rezept.
     *
     * @param rezid ID des Rezeptes, dessen Zutaten geliefert werden soll.
     * @return Liste von ZutatGrenz-Instanzen
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public List<ZutatGrenz> getZutatenVonRezeptByID(Integer rezid) throws IOException, JSONException {
        List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
        List<Zutat> zutatList = zutatService.getZutatenByRezeptID(rezid);
        if(!zutatList.isEmpty()){
            for(Zutat zutat : zutatList){
                zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
            }
        }
        return zutatGrenzList;
    }

    /**
     * Liefert eine Liste von RezeptGrenz-Instanzen, die ein User schonmal gekocht hat.
     *
     * @param userid Id des Users, dessen gekochten Rezepte geliefert werden soll.
     * @return Liste von RezeptGrenz-Instanzen.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public List<RezeptGrenz> getRezeptByUserId(Integer userid) throws IOException, JSONException {
        List<RezeptGrenz> rezeptGrenzList = new ArrayList<>();
        List<Rezept> rezeptList = rezeptService.getRezepteByUserID(userid);
        if(rezeptList!=null){
            for (Rezept rezept : rezeptList){
                rezeptGrenzList.add(new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getBeschreibung(),rezept.getSchritte(),
                        rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getKochzeit(),rezept.getBild(),rezept.getMenge()));
            }
        }
        return rezeptGrenzList;
    }

    /**
     * Fügt ein Rezept, dass ein User gekocht hat in seine Rezepteliste hinzu.
     *
     * @param userid ID des Users.
     * @param rezid ID des Rezeptes.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public void insertRezeptForUser(Integer userid, Integer rezid) throws IOException, JSONException {
        rezeptService.insertRezeptForUser(rezid,userid);
    }
}
