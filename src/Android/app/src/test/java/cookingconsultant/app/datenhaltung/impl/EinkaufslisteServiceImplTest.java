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
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.entities.ZutatState;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.UserService;
import cookingconsultant.app.fachlogik.services.Constants;

import static org.junit.Assert.*;

public class EinkaufslisteServiceImplTest {
    private static EinkaufslisteServiceImpl classToTest = new EinkaufslisteServiceImpl();
    private static final String URL_GET_EINKAUFSLISTEN_BY_USER_ID = Constants.IP_SERVER + "getEinkaufslisteByUserID.php";

    /**
     * /TF09/ Korrektes Anzeigen der Einkaufslisten eines Benutzers
     * Anwendungsfall
     * /LF06/
     *
     * Schritte
     * Nach Einkaufslisten des Benutzers mit ID 1 suchen
     *
     * erwartetes Ergebnis
     * Liste von Einkaufslisten des Benutzers mit der ID 1 wird als Ergebnis zurückgegeben
     *
     * Ergebnis
     * Liste von Einkaufslisten des Benutzers mit der ID 1 wird als Ergebnis zurückgegeben
     */
    @Test
    public void korrektesAnzeigenEinkaufslisten() throws IOException, JSONException {

        URL myurl = new URL(URL_GET_EINKAUFSLISTEN_BY_USER_ID + "?userid=1");
        HttpURLConnection httpURLConnection = (HttpURLConnection) myurl.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();


        InputStream is = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line = "";

        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String data = stringBuilder.toString();

        JSONArray jsonArray = new JSONArray(data);
        JSONObject jobj;
        List<Einkaufsliste> rueckgabe = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jobj = jsonArray.getJSONObject(i);
            if (jobj != null) {
                Einkaufsliste einkaufsliste = new Einkaufsliste(jobj.getInt("einkid"), jobj.getString("zustand"), jobj.getInt("menge"));
                UserService userService = new UserServiceImpl();
                User user = userService.getUserByID(jobj.getInt("userid"));
                einkaufsliste.setUser(user);
                RezeptService rezeptService = new RezeptServiceImpl();
                Rezept rezept = rezeptService.getRezeptByID(jobj.getInt("rezid"));
                einkaufsliste.setRezept(rezept);
                einkaufsliste.setZutatStateList(classToTest.getEinkaufslisteZutatState(jobj.getInt("einkid")));
                rueckgabe.add(einkaufsliste);
            }
        }
        assertNotSame(rueckgabe, classToTest.getEinkaufslistenByUserID(1));
        assertEquals(rueckgabe, classToTest.getEinkaufslistenByUserID(1));
    }

    /**
     * /TF10/ Fehlerhaftes Anzeigen einer Einkaufsliste
     * Anwendungsfall
     * /LF06/
     *
     * Nach Einkaufslisten des Benutzers mit ID 5 suchen (Benutzer existiert nicht in der Datenbank)
     *
     * erwartetes Ergebnis
     * Liste von Einkaufslisten des Benutzers mit der ID 5 wird als Ergebnis zurückgegeben
     *
     * Ergebnis
     * Der Benutzer erhält eine leere Liste, da der Benutzer nicht in der Datenbank vorhanden ist
     */
    @Test
    public void fehlerhaftesAnzeigenEinkaufslisten() throws IOException, JSONException {
        assertTrue(classToTest.getEinkaufslistenByUserID(5).isEmpty());
    }

    /**
     * /TF11/ Korrektes Abarbeiten einer Einkaufsliste
     * Anwendungsfall
     * /LF07/
     *
     * Schritte
     * Jede Zutat der Einkaufsliste 1 wird auf true gesetzt
     *
     * erwartetes Ergebnis
     * Alle Zustände der Einkaufsliste sind true
     *
     * Ergebnis
     * Alle Zustände der Einkaufsliste sind true
     */
    @Test
    public void korrektesAbarbeitenEinkaufsliste() throws IOException, JSONException {
        List<ZutatState> testObjekt = classToTest.getEinkaufslisteZutatState(1);
        for(ZutatState z : testObjekt)
        {
            assertFalse(z.getState());
            classToTest.changeZutatState(z.getId(), true);
        }

        testObjekt = classToTest.getEinkaufslisteZutatState(1);
        for(ZutatState z : testObjekt)
            assertTrue(z.getState());
    }

    /**
     * /TF12/ Fehlerhaftes Abarbeiten einer Einkaufsliste
     * Anwendungsfall
     * /LF07/
     *
     * Schritte
     * Jede Zutat der Einkaufsliste 1 wird auf false gesetzt
     *
     * erwartetes Ergebnis
     * Alle Zustände der Einkaufsliste sind true
     *
     * Ergebnis
     * Alle Zustände der Einkaufsliste sind false
     */
    @Test
    public void fehlerhaftesAbarbeitenEinkaufsliste() throws IOException, JSONException {
        List<ZutatState> testObjekt = classToTest.getEinkaufslisteZutatState(2);
        for(ZutatState z : testObjekt)
        {
            assertFalse(z.getState());
            classToTest.changeZutatState(z.getId(), false);
        }

        testObjekt = classToTest.getEinkaufslisteZutatState(2);
        for(ZutatState z : testObjekt)
            assertFalse(z.getState());
    }

    /**
     * /TF16/ Korrektes Einfügen einer Einkaufsliste
     * Anwendungsfall
     * /LF011/
     *
     * Schritte
     * Erstellen einer neuen Einkaufsliste
     *
     * Einfügen der neuen Einkaufsliste in die Datenbank
     *
     * erwartetes Ergebnis
     * True
     *
     * Ergebnis
     * True
     */
    @Test
    public void korrektesEinfuegenEinkaufsliste() throws IOException, JSONException {
        Einkaufsliste testObjekt = new Einkaufsliste(null, "status", 3);
        assertTrue(classToTest.addEinkaufsliste(testObjekt));
    }

    /**
     * /TF17/ Fehlerhaftes Einfügen einer Einkaufsliste
     * Anwendungsfall
     * /LF011/
     *
     * Schritte
     * Erstellen einer neuen fehlerhaften Einkaufsliste
     *
     * Einfügen der neuen Einkaufsliste in die Datenbank
     *
     * erwartetes Ergebnis
     * True
     *
     * Ergebnis
     * False
     */
    @Test
    public void fehlerhaftesEinfuegenEinkaufsliste() throws IOException, JSONException {
        Einkaufsliste testObjekt = new Einkaufsliste(5, "status", 3);
        assertFalse(classToTest.addEinkaufsliste(testObjekt));
    }
}
