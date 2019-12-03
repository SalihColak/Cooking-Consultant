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

public class EinkaufslisteVerwaltungImpl implements EinkaufslisteVerwaltung {


    private EinkaufslisteService einkaufslisteService;

    public EinkaufslisteVerwaltungImpl(){
        einkaufslisteService = new EinkaufslisteServiceImpl();
    }

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

    @Override
    public boolean deleteEinkaufslisteByID(Integer einkid) throws IOException, JSONException {
        if(einkaufslisteService.deleteEinkaufslisteByID(einkid)){
            return true;
        }
        return false;
    }

    @Override
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) throws IOException, JSONException {
        if(einkaufslisteService.changeZustandEinkaufslisteByID(einkid,neuerZustand)){
            return true;
        }
        return false;
    }

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

    @Override
    public void changeZutatState(Integer ein2zutid, boolean state) throws IOException {
        einkaufslisteService.changeZutatState(ein2zutid,state);
    }
}
