package cookingconsultant.app.datenhaltung.entities;

import java.util.List;

public class Einkaufsliste {

    private Integer einkid;
    private String zustand;

    private List<Zutat> zutaten;
    private User user;

    public Einkaufsliste(Integer einkid){
        this.einkid = einkid;
    }

    public Einkaufsliste(Integer einkid, String zustand) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
