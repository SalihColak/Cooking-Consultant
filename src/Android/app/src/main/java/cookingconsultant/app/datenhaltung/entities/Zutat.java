package cookingconsultant.app.datenhaltung.entities;

public class Zutat {

    private Integer zutid;
    private String name;
    private String beschreibung;
    private String bild;

    public Zutat(Integer zutid){
        this.zutid = zutid;
    }

    public Zutat(Integer zutid, String name, String beschreibung, String bild) {
        this.zutid = zutid;
        this.name = name;
        this.beschreibung = beschreibung;
        this.bild = bild;
    }

    public Integer getZutid() {
        return zutid;
    }

    public void setZutid(Integer zutid) {
        this.zutid = zutid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }
}
