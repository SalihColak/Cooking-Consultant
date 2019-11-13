package cookingconsultant.app.datenhaltung.impl;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.ZutatService;

public class ZutatServiceImpl implements ZutatService {
    @Override
    public Zutat getZutatByID(Integer zutid) {
        return new Zutat(1,"Wasser","Liter","./bilder/zutaten/wasser.png");
    }

    @Override
    public List<Zutat> getZutatenByRezeptID(Integer rezid) throws IOException, JSONException {
        RezeptService rezeptService = new RezeptServiceImpl();
        return rezeptService.getRezeptByID(rezid).getZutaten();
    }
}
