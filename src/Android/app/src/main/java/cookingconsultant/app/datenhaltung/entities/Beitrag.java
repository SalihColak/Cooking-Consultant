package cookingconsultant.app.datenhaltung.entities;

public class Beitrag {

    private Integer beitid;
    private String titel;
    private String inhalt;

    public Beitrag(Integer beitid){
        this.beitid = beitid;
    }

    public Beitrag(Integer beitid, String titel, String inhalt) {
        this.beitid = beitid;
        this.titel = titel;
        this.inhalt = inhalt;
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
