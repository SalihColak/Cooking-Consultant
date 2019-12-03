package cookingconsultant.app.datenhaltung.entities;

import java.util.List;

public class Einkaufsliste {

    private Integer einkid;
    private String zustand;
    private Integer portion;

    private List<ZutatState> zutatStateList;
    private Rezept rezept;
    private User user;

    public Einkaufsliste(Integer einkid){
        this.einkid = einkid;
    }

    public Einkaufsliste(Integer einkid, String zustand, Integer portion) {
        this.einkid = einkid;
        this.zustand = zustand;
        this.portion = portion;
    }

    public List<ZutatState> getZutatStateList() {
        return zutatStateList;
    }

    public void setZutatStateList(List<ZutatState> zutatStateList) {
        this.zutatStateList = zutatStateList;
    }

    public Integer getPortion() {
        return portion;
    }

    public void setPortion(Integer portion) {
        this.portion = portion;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rezept getRezept() {
        return rezept;
    }

    public void setRezept(Rezept rezept) {
        this.rezept = rezept;
    }
}
