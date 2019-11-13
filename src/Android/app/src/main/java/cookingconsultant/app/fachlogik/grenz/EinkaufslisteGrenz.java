package cookingconsultant.app.fachlogik.grenz;

import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;

public class EinkaufslisteGrenz {

    private Integer einkid;
    private String zustand;

    private List<Zutat> zutaten;
    private UserGrenz user;

    public EinkaufslisteGrenz(Integer einkid){
        this.einkid = einkid;
    }

    public EinkaufslisteGrenz(Integer einkid, String zustand) {
        this.einkid = einkid;
        this.zustand = zustand;
    }

    public Integer getEinkid() {
        return einkid;
    }

    public void setEinkid(Integer einkid) {
        this.einkid = einkid;
    }

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    public String getZustand() {
        return zustand;
    }

    public void setZustand(String zustand) {
        this.zustand = zustand;
    }

    public UserGrenz getUser() {
        return user;
    }

    public void setUser(UserGrenz user) {
        this.user = user;
    }

}
