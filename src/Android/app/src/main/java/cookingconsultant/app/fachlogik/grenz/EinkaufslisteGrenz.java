package cookingconsultant.app.fachlogik.grenz;

import java.util.List;


public class EinkaufslisteGrenz {

    private Integer einkid;
    private String zustand;

    private List<ZutatGrenz> zutaten;
    private UserGrenz user;
    private RezeptGrenz rezept;

    public EinkaufslisteGrenz(Integer einkid){
        this.einkid = einkid;
    }

    public EinkaufslisteGrenz(Integer einkid, String zustand, List<ZutatGrenz> zutaten, UserGrenz user, RezeptGrenz rezept) {
        this.einkid = einkid;
        this.zustand = zustand;
        this.zutaten = zutaten;
        this.user = user;
        this.rezept = rezept;
    }

    public RezeptGrenz getRezept() {
        return rezept;
    }

    public void setRezept(RezeptGrenz rezept) {
        this.rezept = rezept;
    }

    public Integer getEinkid() {
        return einkid;
    }

    public void setEinkid(Integer einkid) {
        this.einkid = einkid;
    }

    public List<ZutatGrenz> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<ZutatGrenz> zutaten) {
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
