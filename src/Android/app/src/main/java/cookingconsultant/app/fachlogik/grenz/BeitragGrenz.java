package cookingconsultant.app.fachlogik.grenz;

public class BeitragGrenz {

    private Integer beitid;
    private String titel;
    private String inhalt;
    private String kategorie;

    public BeitragGrenz(Integer beitid){
        this.beitid = beitid;
    }

    public BeitragGrenz(Integer beitid, String titel, String inhalt, String kategorie) {
        this.beitid = beitid;
        this.titel = titel;
        this.inhalt = inhalt;
        this.kategorie = kategorie;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public Integer getBeitid() {
        return beitid;
    }

    public void setBeitid(Integer beitid) {
        this.beitid = beitid;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

}
