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

/**
 * KochlexikonVerwaltungImpl ist die Systemlogik für das Kochlexikon der Applikation.
 * Sie greift auf Klassen der Datenhaltung zu.
 *
 * @author Salih Colak
 */
public class KochlexikonVerwaltungImpl implements KochlexikonVerwaltung {

    private KochlexikonService kochlexikonService;

    public KochlexikonVerwaltungImpl(){
        kochlexikonService = new KochlexikonServiceImpl();
    }

    /**
     * Diese Methode sucht nach einem Beitrag in der DB und liefert eine äquivalente BeitragGrenz-Instanz.
     *
     * @param beitid ID des Beitrags, dass gesucht werden soll.
     * @return BeitragGrenz-Instanz, falls der Beitrag mit der ID <<beitid>> in der DB existiert;
     *         null, falls der Beitrag nicht existiert.
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public BeitragGrenz getBeitragByID(Integer beitid) throws IOException, JSONException {
        Beitrag beitrag = kochlexikonService.getBeitragByID(beitid);
        if(beitrag!=null){
            return new BeitragGrenz(beitrag.getBeitid(),beitrag.getTitel(),beitrag.getInhalt(),beitrag.getKategorie());
        }
        return null;
    }


    /**
     * Diese Methode sucht nach einer Kategorie von Beiträgen in der DB und liefert eine äquivalente Liste von BeitragGrenz-Instanzen.
     *
     * @param kategorie Beitrag-Kategorie nach der gesucht werden soll.
     * @return Liste von BeitragGrenz-Instanzen.
     * @throws IOException
     * @throws JSONException
     */
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
