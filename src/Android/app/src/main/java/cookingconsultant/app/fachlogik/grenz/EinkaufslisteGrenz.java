package cookingconsultant.app.fachlogik.grenz;

import java.io.Serializable;
import java.util.List;


public class EinkaufslisteGrenz implements Serializable {

    private Integer einkid;
    private String zustand;
    private Integer portion;
    private List<ZutatStateGrenz> zutatStateList;

    private UserGrenz user;
    private RezeptGrenz rezept;

    public EinkaufslisteGrenz(Integer einkid){
        this.einkid = einkid;
    }

    public EinkaufslisteGrenz(Integer einkid, String zustand, UserGrenz user, RezeptGrenz rezept, Integer portion) {
        this.einkid = einkid;
        this.zustand = zustand;
        this.user = user;
        this.rezept = rezept;
        this.portion = portion;
    }

    public List<ZutatStateGrenz> getZutatStateList() {
        return zutatStateList;
    }

    public void setZutatStateList(List<ZutatStateGrenz> zutatStateList) {
        this.zutatStateList = zutatStateList;
    }

    public Integer getPortion() {
        return portion;
    }

    public void setPortion(Integer portion) {
        this.portion = portion;
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
