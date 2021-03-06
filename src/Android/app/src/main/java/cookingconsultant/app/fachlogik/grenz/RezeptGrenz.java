package cookingconsultant.app.fachlogik.grenz;

import java.io.Serializable;
import java.util.List;


public class RezeptGrenz implements Serializable {

    private Integer rezid;
    private String name;
    private List<String> schritte;
    private String art;
    private String anlass;
    private String praeferenz;
    private String bild;
    private String kochzeit;
    private String beschreibung;
    private String menge;

    private List<ZutatGrenz> zutaten;

    public RezeptGrenz(Integer rezid){
        this.rezid = rezid;
    }

    public RezeptGrenz(Integer rezid, String name,String beschreibung ,List<String> schritte, String art, String anlass, String praeferenz,String kochzeit, String bild, String menge) {
        this.rezid = rezid;
        this.name = name;
        this.schritte = schritte;
        this.art = art;
        this.anlass = anlass;
        this.praeferenz = praeferenz;
        this.bild = bild;
        this.zutaten = zutaten;
        this.kochzeit = kochzeit;
        this.beschreibung = beschreibung;
        this.menge = menge;
    }

    public String getMenge() {
        return menge;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRezid() {
        return rezid;
    }

    public void setRezid(Integer rezid) {
        this.rezid = rezid;
    }

    public List<String> getSchritte() {
        return schritte;
    }

    public void setSchritte(List<String> schritte) {
        this.schritte = schritte;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getAnlass() {
        return anlass;
    }

    public void setAnlass(String anlass) {
        this.anlass = anlass;
    }

    public String getPraeferenz() {
        return praeferenz;
    }

    public void setPraeferenz(String praeferenz) {
        this.praeferenz = praeferenz;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public List<ZutatGrenz> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<ZutatGrenz> zutaten) {
        this.zutaten = zutaten;
    }

    public String getKochzeit() {
        return kochzeit;
    }

    public void setKochzeit(String kochzeit) {
        this.kochzeit = kochzeit;
    }
}
