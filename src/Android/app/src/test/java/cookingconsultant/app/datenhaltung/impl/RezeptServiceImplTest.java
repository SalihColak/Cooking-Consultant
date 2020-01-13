package cookingconsultant.app.datenhaltung.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.fachlogik.services.Constants;

import static org.junit.Assert.*;

public class RezeptServiceImplTest {
    static private RezeptServiceImpl classToTest = new RezeptServiceImpl();
    private static final String URL_GET_REZEPTE_BY_QUERY = Constants.IP_SERVER+"getRezeptByArtAnlassPraeferenz.php";

    /**
     * /TF03/ Korrektes Suchen von Rezepten
     * Anwendungsfall
     * /LF02/
     *
     * Schritte
     * Suchen nach Rezepten mit mindestens einem Anlass, Art, Präferenz (Hier: ESSEN ZU ZWEIT, BRUNCH, ASIATISCH)
     *
     * erwartetes Ergebnis
     * Der Benutzer bekommt eine Liste aus Rezepten als Ergebnis
     *
     * Ergebnis
     * Der Benutzer bekommt eine Liste aus Rezepten als Ergebnis
     */
    @Test
    public void korrektesSuchen() throws IOException, JSONException {
        URL myurl = new URL(URL_GET_REZEPTE_BY_QUERY+"?art='BRUNCH'&anlass='ESSEN ZU ZWEIT'&praeferenz='ASIATISCH'");
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();


        InputStream is = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line ="";

        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        String data = stringBuilder.toString();

        JSONArray jsonArray = new JSONArray(data);
        JSONObject jobj;
        List<Rezept> rueckgabe = new ArrayList<>();
        for (int i = 0; i<jsonArray.length();i++){
            jobj = jsonArray.getJSONObject(i);
            List<String> schritte;
            schritte = Arrays.asList(jobj.getString("schritte").split(";"));
            rueckgabe.add(new Rezept(jobj.getInt("rezid"), jobj.getString("name"), jobj.getString("beschreibung"), schritte,
                    jobj.getString("art"), jobj.getString("anlass"), jobj.getString("praeferenz"), jobj.getString("kochzeit") + "min",
                    jobj.getString("bild"), jobj.getString("menge")));
        }
        String query = "'BRUNCH';'ESSEN ZU ZWEIT';'ASIATISCH'";
        assertNotSame(rueckgabe, classToTest.getRezepteByQuery(query));
        assertEquals(rueckgabe, classToTest.getRezepteByQuery(query));
    }

    /**
     * /TF04/ Fehlerhaftes Suchen von Rezepten (Art)
     * Anwendungsfall
     * /LF02/
     *
     * Schritte
     * Suchen nach Rezepten mit fehlerhafter Art (Ansonsten derselbe Anlass/Präferenz wie /TF03/)
     *
     * erwartetes Ergebnis
     * Der Benutzer bekommt eine Liste aus Rezepten als Ergebnis
     *
     * Ergebnis
     * Der Benutzer bekommt eine leere Liste
     */
    @Test
    public void fehlerhaftesSuchenArt() throws IOException, JSONException {
        String query = "'MITTERNACHTSSNACK';'ESSEN ZU ZWEIT';'ASIATISCH'";
        assertTrue(classToTest.getRezepteByQuery(query).isEmpty());
    }

    /**
     * /TF05/ Fehlerhaftes Suchen von Rezepten (Anlass)
     * Anwendungsfall
     * /LF02/
     *
     * Schritte
     * Suchen nach Rezepten mit fehlerhaftem Anlass (Ansonsten dieselbe Art/Präferenz wie /TF03/)
     *
     * erwartetes Ergebnis
     * Der Benutzer bekommt eine Liste aus Rezepten als Ergebnis
     *
     * Ergebnis
     * Der Benutzer bekommt eine leere Liste
     */
    @Test
    public void fehlerhaftesSuchenAnlass() throws IOException, JSONException {
        String query = "'BRUNCH';'DINNER FOR ONE';'ASIATISCH'";
        assertTrue(classToTest.getRezepteByQuery(query).isEmpty());
    }

    /**
     * /TF06/ Fehlerhaftes Suchen von Rezepten (Präferenz)
     * Anwendungsfall
     * /LF02/
     *
     * Schritte
     * Suchen nach Rezepten mit fehlerhafter Präferenz (Ansonsten derselbe Anlass/Art wie /TF03/)
     *
     * erwartetes Ergebnis
     * Der Benutzer bekommt eine Liste aus Rezepten als Ergebnis
     *
     * Ergebnis
     * Der Benutzer bekommt eine leere Liste
     */
    @Test
    public void fehlerhaftesSuchenPraeferenz() throws IOException, JSONException {

        String query = "'BRUNCH';'ESSEN ZU ZWEIT';'UGANDISCH'";
        assertTrue(classToTest.getRezepteByQuery(query).isEmpty());
    }
}
