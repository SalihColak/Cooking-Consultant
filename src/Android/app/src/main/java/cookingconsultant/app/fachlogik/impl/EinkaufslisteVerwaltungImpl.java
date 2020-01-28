package cookingconsultant.app.fachlogik.impl;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.entities.ZutatState;
import cookingconsultant.app.datenhaltung.impl.EinkaufslisteServiceImpl;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatStateGrenz;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;


/**
 * EinkaufslisteVerwaltungImpl ist die Systemlogik für die Einkaufslisten der Applikation.
 * Sie greift auf Klassen der Datenhaltung zu.
 *
 * @author Salih Colak
 */
public class EinkaufslisteVerwaltungImpl implements EinkaufslisteVerwaltung {


    private EinkaufslisteService einkaufslisteService;

    public EinkaufslisteVerwaltungImpl(){
        einkaufslisteService = new EinkaufslisteServiceImpl();
    }

    /**
     * Diese Methode liefert eine EinkaufslisteGrenz-Instanz, die äquivalent mit einer Einkaufsliste-Instanz ist.
     *
     * @param einkid Die ID der Einkaufsliste, welche gesucht werden soll.
     * @return EinkaufslisteGrenz-Instanz mit allen Informationen zu der EInkaufsliste mit der ID <<einkid>>; null falls die
     * Einkaufsliste nicht existiert.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public EinkaufslisteGrenz getEinkaufslisteByID(Integer einkid) throws IOException, JSONException {
        Einkaufsliste einkaufsliste = einkaufslisteService.getEinkaufslisteByID(einkid);
        if(einkaufsliste!=null){
            User user = einkaufsliste.getUser();
            UserGrenz userGrenz = new UserGrenz(user.getUserid(),user.getTitel(),user.getName(),user.getVorname(),
                    user.getGeschlecht(),user.getGeburtsdatum(), user.isAdmin(),user.getEmail());
            List<Zutat> zutatList = einkaufsliste.getRezept().getZutaten();
            List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
            for(Zutat zutat : zutatList){
                zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
            }
            Rezept rezept = einkaufsliste.getRezept();
            RezeptGrenz rezeptGrenz = new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getBeschreibung(),rezept.getSchritte(),rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getKochzeit(),rezept.getBild(),rezept.getMenge());
            rezeptGrenz.setZutaten(zutatGrenzList);
            return new EinkaufslisteGrenz(einkaufsliste.getEinkid(),einkaufsliste.getZustand(),userGrenz,rezeptGrenz,einkaufsliste.getPortion());
        }
        return null;
    }

    /**
     * Diese Methode liefert eine Liste von EinkaufslisteGrenz-Instanzen, die äquivalent mit einer Liste von Einkaufsliste-Instanzen ist.
     *
     * @param userid Die ID des Benutzers dessen Eikaufslisten aufgerufen werden sollen
     * @return Liste von EinkaufslisteGrenz-Instanzen des Users mit der ID <<userid>>>
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public List<EinkaufslisteGrenz> getEinkaufslistenByUserID(Integer userid) throws IOException, JSONException {
        List<Einkaufsliste> einkaufslisteList = einkaufslisteService.getEinkaufslistenByUserID(userid);
        List<EinkaufslisteGrenz> einkaufslisteGrenzList = new ArrayList<>();
        if(!einkaufslisteList.isEmpty()){
            for(Einkaufsliste einkaufsliste : einkaufslisteList){
                User user = einkaufsliste.getUser();
                UserGrenz userGrenz = new UserGrenz(user.getUserid(),user.getTitel(),user.getName(),user.getVorname(),
                        user.getGeschlecht(),user.getGeburtsdatum(), user.isAdmin(),user.getEmail());
                List<Zutat> zutatList = einkaufsliste.getRezept().getZutaten();
                List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
                for(Zutat zutat : zutatList){
                    zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
                }
                Rezept rezept = einkaufsliste.getRezept();
                RezeptGrenz rezeptGrenz = new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getBeschreibung(),rezept.getSchritte(),rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getKochzeit(),rezept.getBild(),rezept.getMenge());
                rezeptGrenz.setZutaten(zutatGrenzList);
                EinkaufslisteGrenz einkaufslisteGrenz = new EinkaufslisteGrenz(einkaufsliste.getEinkid(),einkaufsliste.getZustand(),
                        userGrenz,rezeptGrenz,einkaufsliste.getPortion());
                List<ZutatStateGrenz> zutatStateGrenzList = new ArrayList<>();
                for(ZutatState zutatState : einkaufsliste.getZutatStateList()){
                    ZutatGrenz zutatGrenz = new ZutatGrenz(zutatState.getZutat().getZutid(),zutatState.getZutat().getName(),zutatState.getZutat().getEinheit(),zutatState.getZutat().getBild());
                    ZutatStateGrenz zutatStateGrenz = new ZutatStateGrenz(zutatGrenz,zutatState.getState());
                    zutatStateGrenz.setId(zutatState.getId());
                    zutatStateGrenzList.add(zutatStateGrenz);
                }
                einkaufslisteGrenz.setZutatStateList(zutatStateGrenzList);
                einkaufslisteGrenzList.add(einkaufslisteGrenz);
            }
        }
        return einkaufslisteGrenzList;
    }

    /**
     * Diese Methode entfernt eine Einkaufsliste aus der DB, welche die ID <<einkid>> hat.
     *
     * @param einkid Die ID der Einkaufsliste, welche aus der DB entfernt werden soll.
     * @return true, falls die Einkaufsliste aus der DB entfernt wurde;
     *         false, falls die Einkaufsliste nicht aus der DB entfernt wurde oder falls Sie garnicht in der DB vorhanden ist.
     * @throws IOException
     * @throws JSONException
     */

    @Override
    public boolean deleteEinkaufslisteByID(Integer einkid) throws IOException, JSONException {
        if(einkaufslisteService.deleteEinkaufslisteByID(einkid)){
            return true;
        }
        return false;
    }

    /**
     * Diese Methode ändert den Zustand einer Einkaufsliste in der DB.
     *
     * @param einkid ID der EInkaufsliste, dessen Zustand geändert werden soll.
     * @param neuerZustand Zustand, den die Einkaufsliste in der DB einnehmen soll.
     * @return true, falls der Zustand erfolgreich gesetzt wurde;
     *         false, falls die EInkaufsliste nicht in der DB existiert oder der Zustand nicht gesetzt wird.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) throws IOException, JSONException {
        if(einkaufslisteService.changeZustandEinkaufslisteByID(einkid,neuerZustand)){
            return true;
        }
        return false;
    }

    /**
     * Fügt zu der übergebenen EinkaufslisteGrenz-Instanz eine äquivalente Einkaufsliste-Instanz in die DB ein.
     *
     * @param einkaufslisteGrenz EinkaufslisteGrenz-Instanz, die der DB hinzugefügt werden soll.
     * @return true, falls die Einkaufsliste der DB hinzugefügt wurde;
     *         false, falls die EInkaufsliste nicht der DB hinzugefügt wurde.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public boolean addEinkaufsliste(EinkaufslisteGrenz einkaufslisteGrenz) throws IOException, JSONException {
        Einkaufsliste einkaufsliste = new Einkaufsliste(einkaufslisteGrenz.getEinkid(),einkaufslisteGrenz.getZustand(),einkaufslisteGrenz.getPortion());
        RezeptGrenz rezeptGrenz = einkaufslisteGrenz.getRezept();

        Rezept rezept = new Rezept(rezeptGrenz.getRezid(),rezeptGrenz.getName(),rezeptGrenz.getBeschreibung(),rezeptGrenz.getSchritte(),rezeptGrenz.getArt(),rezeptGrenz.getAnlass(),rezeptGrenz.getPraeferenz(),
                rezeptGrenz.getKochzeit(),rezeptGrenz.getBild(),rezeptGrenz.getMenge());
        List<Zutat> zutatList = new ArrayList<>();
        for(ZutatGrenz zutatGrenz : rezeptGrenz.getZutaten()){
            zutatList.add(new Zutat(zutatGrenz.getZutid(),zutatGrenz.getName(),zutatGrenz.getEinheit(),zutatGrenz.getBild()));
        }
        rezept.setZutaten(zutatList);

        einkaufsliste.setRezept(rezept);
        UserGrenz userGrenz = einkaufslisteGrenz.getUser();
        User user = new User(userGrenz.getUserid(),userGrenz.getTitel(),userGrenz.getName(),userGrenz.getVorname(),userGrenz.getGeschlecht(),userGrenz.getGeburtsdatum(),userGrenz.isAdmin(),userGrenz.getEmail());
        einkaufsliste.setUser(user);
        if(einkaufslisteService.addEinkaufsliste(einkaufsliste)){
            return true;
        }
        return false;
    }

    /**
     * Ändert den Zustand einer Zutat in der Einkaufsliste.
     *
     * @param ein2zutid ID der Zutatposition in der Einkaufsliste, dessen Zustand geändert wird.
     * @param state neuer Zustand der Zutat der Einkaufsliste.
     * @throws IOException
     */
    @Override
    public void changeZutatState(Integer ein2zutid, boolean state) throws IOException {
        einkaufslisteService.changeZutatState(ein2zutid,state);
    }
}
