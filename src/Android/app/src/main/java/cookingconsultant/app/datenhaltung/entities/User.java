package cookingconsultant.app.datenhaltung.entities;

import java.time.LocalDate;
import java.util.List;

public class User {

    private Integer userid;
    private String titel;
    private String name;
    private String vorname;
    private String geschlecht;
    private LocalDate geburtsdatum;
    private boolean admin;

    private List<Einkaufsliste> einkaufslisten;

    public User(Integer userid){
        this.userid = userid;
    }

    public User(Integer userid, String titel, String name, String vorname, String geschlecht, LocalDate geburtsdatum, boolean admin) {
        this.userid = userid;
        this.titel = titel;
        this.name = name;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.geburtsdatum = geburtsdatum;
        this.admin = admin;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Einkaufsliste> getEinkaufslisten() {
        return einkaufslisten;
    }

    public void setEinkaufslisten(List<Einkaufsliste> einkaufslisten) {
        this.einkaufslisten = einkaufslisten;
    }
}
