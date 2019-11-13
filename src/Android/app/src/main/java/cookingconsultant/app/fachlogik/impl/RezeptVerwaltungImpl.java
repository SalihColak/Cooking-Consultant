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

public class RezeptVerwaltungImpl implements RezeptVerwaltung {

    private RezeptService rezeptService;
    private ZutatService zutatService;

    public RezeptVerwaltungImpl(){
        zutatService = new ZutatServiceImpl();
        rezeptService = new RezeptServiceImpl();
    }

    @Override
    public List<RezeptGrenz> getRezeptByQuery(String query) throws IOException, JSONException {
        List<RezeptGrenz> rezeptGrenzList = new ArrayList<>();
        List<Rezept> rezeptList = rezeptService.getRezepteByQuery(query);
        if(rezeptList!=null){
            for (Rezept rezept : rezeptList){
                List<Zutat> zutatList = rezept.getZutaten();
                List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
                for(Zutat zutat : zutatList){
                    zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
                }
                rezeptGrenzList.add(new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getSchritte(),
                        rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getBild(),zutatGrenzList));
            }
        }
        return rezeptGrenzList;
    }

    @Override
    public RezeptGrenz getRezeptByID(Integer rezid) throws IOException, JSONException {
        Rezept rezept = rezeptService.getRezeptByID(rezid);
        if(rezept != null){
            List<Zutat> zutatList = rezept.getZutaten();
            List<ZutatGrenz> zutatGrenzList = new ArrayList<>();
            for(Zutat zutat : zutatList){
                zutatGrenzList.add(new ZutatGrenz(zutat.getZutid(),zutat.getName(),zutat.getEinheit(),zutat.getBild()));
            }
            return new RezeptGrenz(rezept.getRezid(),rezept.getName(),rezept.getSchritte(),
                    rezept.getArt(),rezept.getAnlass(),rezept.getPraeferenz(),rezept.getBild(),zutatGrenzList);
        }
        return null;
    }

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
}
