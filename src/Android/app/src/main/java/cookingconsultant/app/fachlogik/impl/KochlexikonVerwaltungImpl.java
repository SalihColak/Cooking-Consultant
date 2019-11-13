package cookingconsultant.app.fachlogik.impl;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Beitrag;
import cookingconsultant.app.datenhaltung.impl.KochlexikonServiceImpl;
import cookingconsultant.app.datenhaltung.services.KochlexikonService;
import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;
import cookingconsultant.app.fachlogik.services.KochlexikonVerwaltung;

public class KochlexikonVerwaltungImpl implements KochlexikonVerwaltung {

    private KochlexikonService kochlexikonService;

    public KochlexikonVerwaltungImpl(){
        kochlexikonService = new KochlexikonServiceImpl();
    }

    @Override
    public BeitragGrenz getBeitragByID(Integer beitid) throws IOException, JSONException {
        Beitrag beitrag = kochlexikonService.getBeitragByID(beitid);
        if(beitrag!=null){
            return new BeitragGrenz(beitrag.getBeitid(),beitrag.getTitel(),beitrag.getInhalt(),beitrag.getKategorie());
        }
        return null;
    }

    @Override
    public List<BeitragGrenz> getBeitraegeByKategorie(String kategorie) throws IOException, JSONException {
        List<BeitragGrenz> beitragGrenzList = new ArrayList<>();
        List<Beitrag> beitragList = kochlexikonService.getBeitraegeByKategorie(kategorie);
        if(!beitragList.isEmpty()){
            for (Beitrag beitrag : beitragList){
                beitragGrenzList.add(new BeitragGrenz(beitrag.getBeitid(),beitrag.getTitel(),beitrag.getInhalt(),beitrag.getKategorie()));
            }
        }
        return beitragGrenzList;
    }
}
