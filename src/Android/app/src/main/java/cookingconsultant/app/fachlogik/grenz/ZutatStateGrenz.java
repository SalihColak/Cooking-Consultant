package cookingconsultant.app.fachlogik.grenz;

import java.io.Serializable;

public class ZutatStateGrenz implements Serializable {

    private ZutatGrenz zutatGrenz;
    private boolean status;
    private Integer id;

    public ZutatStateGrenz() {
    }

    public ZutatStateGrenz(ZutatGrenz zutatGrenz, boolean status) {
        this.zutatGrenz = zutatGrenz;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZutatGrenz getZutatGrenz() {
        return zutatGrenz;
    }

    public void setZutatGrenz(ZutatGrenz zutatGrenz) {
        this.zutatGrenz = zutatGrenz;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
