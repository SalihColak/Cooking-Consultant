package cookingconsultant.app.fachlogik.impl;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.impl.EinkaufslisteServiceImpl;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;
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
            List<Zutat> zutatList = einkaufsliste.getZutaten();
            List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
            for(Zutat zutat : zutatList){
                zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
            }
            return new EinkaufslisteGrenz(einkaufsliste.getEinkid(),einkaufsliste.getZustand(),zutatGrenzList,userGrenz);
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
                List<Zutat> zutatList = einkaufsliste.getZutaten();
                List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
                for(Zutat zutat : zutatList){
                    zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
                }
                einkaufslisteGrenzList.add(new EinkaufslisteGrenz(einkaufsliste.getEinkid(),einkaufsliste.getZustand(),zutatGrenzList,userGrenz));
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
}
