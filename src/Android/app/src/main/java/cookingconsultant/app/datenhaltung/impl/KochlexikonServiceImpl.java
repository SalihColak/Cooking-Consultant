package cookingconsultant.app.datenhaltung.impl;

import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Beitrag;
import cookingconsultant.app.datenhaltung.services.KochlexikonService;

public class KochlexikonServiceImpl implements KochlexikonService {
    @Override
    public Beitrag getBeitragByID(Integer beitid) {
        return new Beitrag(4,"Schneidebrett","Ein Schneidebrett ist eine Unterlage zum Schneiden"+
                " von Lebensmitteln. Es dient dazu, dass beim Schneiden weder der Tisch beschädigt"+
                " noch das Messer stumpf wird. Eine Sonderform des Schneidebrettes ist das Jausenbrettl,"+
                " das zwar ein konventionelles Schneidbrett ist, aber primär als Essgeschirr für die Jause"+
                " und nur sekundär der Speisenzubereitung dient.","Utensilien");
    }

    @Override
    public List<Beitrag> getBeitraegeByKategorie(String kategorie) {
        List<Beitrag> mockList = new ArrayList<>();
        mockList.add(new Beitrag(4,"Schneidebrett","Ein Schneidebrett ist eine Unterlage zum Schneiden"+
                " von Lebensmitteln. Es dient dazu, dass beim Schneiden weder der Tisch beschädigt"+
                " noch das Messer stumpf wird. Eine Sonderform des Schneidebrettes ist das Jausenbrettl,"+
                " das zwar ein konventionelles Schneidbrett ist, aber primär als Essgeschirr für die Jause"+
                " und nur sekundär der Speisenzubereitung dient.","Utensilien"));
        mockList.add(new Beitrag(5,"Pfannenwender","Ein Pfannenwender, auch Pfannenmesser, Schlitzwender, Backschaufel, Bratenwender,"
                +"Bratschaufel, Nehmgerät oder Küchenfreund genannt, ist ein Küchengerät zum Wenden von Gargut in der Pfanne.","Utensilien"));
        mockList.add(new Beitrag(53,"Dosenoeffner","Dosenöffner bezeichnet ein Gerät zum vollständigen Öffnen von metallenen"
                +"Konservendosen. Einzelne Löcher, wie in Kondensmilch-Dosen, können auch mit einem Dosenlocher erzeugt werden.","Utensilien"));

        return mockList;
    }
}
