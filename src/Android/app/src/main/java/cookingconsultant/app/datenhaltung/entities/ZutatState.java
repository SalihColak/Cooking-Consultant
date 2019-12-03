package cookingconsultant.app.datenhaltung.entities;

public class ZutatState {

    private Zutat zutat;
    private Boolean state;
    private Integer id;

    public ZutatState() {
    }

    public ZutatState(Zutat zutat, Boolean state) {
        this.zutat = zutat;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Zutat getZutat() {
        return zutat;
    }

    public void setZutat(Zutat zutat) {
        this.zutat = zutat;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
