package cookingconsultant.app.fachlogik.grenz;

public class ZutatGrenz {

    private Integer zutid;
    private String name;
    private String einheit;
    private String bild;

    public ZutatGrenz(Integer zutid){
        this.zutid = zutid;
    }

    public ZutatGrenz(Integer zutid, String name, String einheit, String bild) {
        this.zutid = zutid;
        this.name = name;
        this.einheit = einheit;
        this.bild = bild;
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

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
