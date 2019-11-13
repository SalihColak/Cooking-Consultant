package cookingconsultant.app.datenhaltung.entities;

public class Zutat {

    private Integer zutid;
    private String name;
    private String bild;
    private String einheit;

    public Zutat(Integer zutid){
        this.zutid = zutid;
    }

    public Zutat(Integer zutid, String name, String einheit ,String bild) {
        this.zutid = zutid;
        this.name = name;
        this.bild = bild;
        this.einheit = einheit;
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

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }
}
