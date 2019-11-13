package cookingconsultant.app.fachlogik.grenz;

import java.util.List;


public class UserGrenz {

    private Integer userid;
    private String titel;
    private String name;
    private String vorname;
    private String geschlecht;
    private String geburtsdatum;
    private boolean admin;
    private String passwort;
    private String email;

    private List<EinkaufslisteGrenz> einkaufslisten;

    public UserGrenz(Integer userid){
        this.userid = userid;
    }

    public UserGrenz(Integer userid, String titel, String name, String vorname, String geschlecht, String geburtsdatum, boolean admin, String email) {
        this.userid = userid;
        this.titel = titel;
        this.name = name;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.geburtsdatum = geburtsdatum;
        this.admin = admin;
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<EinkaufslisteGrenz> getEinkaufslisten() {
        return einkaufslisten;
    }

    public void setEinkaufslisten(List<EinkaufslisteGrenz> einkaufslisten) {
        this.einkaufslisten = einkaufslisten;
    }

}
