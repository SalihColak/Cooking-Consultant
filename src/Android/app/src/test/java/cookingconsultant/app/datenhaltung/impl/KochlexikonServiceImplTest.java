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

import cookingconsultant.app.datenhaltung.entities.Beitrag;
import cookingconsultant.app.fachlogik.services.Constants;

import static org.junit.Assert.*;

public class KochlexikonServiceImplTest {
    private static final String URL_GET_BEITRAG_BY_ID = Constants.IP_SERVER+"getBeitragByID.php";
    private static final String URL_GET_BEITRAG = Constants.IP_SERVER+"getBeitrag.php";
    private static KochlexikonServiceImpl classToTest = new KochlexikonServiceImpl();


    /**
     * /TF13/ Anzeigen aller Kochlexikonbeiträge
     * Anwendungsfall
     * /LF09/
     *
     * Schritte
     * Suche nach allen Beiträgen
     *
     * erwartetes Ergebnis
     * Eine Liste aller Beiträge
     *
     * Ergebnis
     * Eine Liste aller Beiträge
     */
    @Test
    public void korrektesAnzeigenBeitraege() throws IOException, JSONException {
        URL myurl = new URL(URL_GET_BEITRAG);
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
        List<Beitrag> rueckgabe = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jobj = jsonArray.getJSONObject(i);
            if (jobj != null) {
                Beitrag beitrag = new Beitrag(jobj.getInt("beitid"), jobj.getString("titel"), jobj.getString("inhalt"), jobj.getString("kategorie"));
                rueckgabe.add(beitrag);
            }
        }
        assertNotSame(rueckgabe, classToTest.getBeitraege());
        List<Beitrag> actual = classToTest.getBeitraege();
        for(int i = 0; i < rueckgabe.size(); i++)
            assertTrue(rueckgabe.get(i).equals(actual.get(i)));
    }

    /**
     * /TF14/ Korrektes Anzeigen eines Kochlexikonbeitrags
     * Anwendungsfall
     * /LF010/
     *
     * Schritte
     * Suche nach Beitrag mit der ID 1
     *
     * erwartetes Ergebnis
     * Der Beitrag mit der ID 1 wird als Ergebnis zurückgegeben
     *
     * Ergebnis
     * Der Beitrag mit der ID 1 wird als Ergebnis zurückgegeben
     */
    @Test
    public void korrektesAnzeigenBeitrag() throws IOException, JSONException {
        URL myurl = new URL(URL_GET_BEITRAG_BY_ID + "?beitid=1");
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
        JSONObject jobj = jsonArray.getJSONObject(0);
        Beitrag rueckgabe = null;
        if (jobj != null) {
            rueckgabe = new Beitrag(jobj.getInt("beitid"), jobj.getString("titel"), jobj.getString("inhalt"), jobj.getString("kategorie"));
        }
        assertNotSame(rueckgabe, classToTest.getBeitragByID(1));
        assertTrue(rueckgabe.equals(classToTest.getBeitragByID(1)));
    }

    /**
     * /TF15/ Fehlerhaftes Anzeigen eines Kochlexikonbeitrags
     * Anwendungsfall
     * /LF010/
     *
     * Schritte
     * Neuen Beitrag mit der ID 5 erstellen
     *
     * Suche nach Beitrag mit der ID 5
     *
     * erwartetes Ergebnis
     * Der Beitrag mit der ID 5 wird als Ergebnis zurückgegeben
     *
     * Ergebnis
     * Null-Referenz, da Beitrag mit ID 5 nicht in der Datenbank vorhanden ist
     */
    @Test(expected = JSONException.class)
    public void fehlerhaftesAnzeigenBeitrag() throws IOException, JSONException {
        classToTest.getBeitragByID(5);
    }
}
