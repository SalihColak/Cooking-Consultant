package cookingconsultant.app.datenhaltung.entities;

import java.util.List;

public class Rezept {

    private Integer rezid;
    private List<String> schritte;
    private String art;
    private String anlass;
    private String praeferenz;
    private String bild;

    private List<Zutat> zutaten;

    public Rezept(Integer rezid){
        this.rezid = rezid;
    }

    public Rezept(Integer rezid, List<String> schritte, String art, String anlass, String praeferenz, String bild) {
        this.rezid = rezid;
        this.schritte = schritte;
        this.art = art;
        this.anlass = anlass;
        this.praeferenz = praeferenz;
        this.bild = bild;
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

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }
}
