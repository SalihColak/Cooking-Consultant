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

import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.fachlogik.services.Constants;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    static private UserServiceImpl classToTest = new UserServiceImpl();
    static private final String URL_GET_USER_BY_LOGIN= Constants.IP_SERVER+"getBenutzerByEmailPasswort.php";
    /**
     * /TF01/ Korrektes Einloggen
     * Anwendungsfall
     * /LF01/
     *
     * Schritte
     * Login mit Credentials test@mail.de / test
     *
     * erwartetes Ergebnis
     * Der Benutzer erhält den entsprechenden Benutzer als Antwort
     *
     * Ergebnis
     * Der Benutzer erhält den entsprechenden Benutzer als Antwort
     */
    @Test
    public void korrektesEinloggen() throws IOException, JSONException {
        URL myurl = new URL(URL_GET_USER_BY_LOGIN+"?email=test@mail.de&passwort=test");
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.setDoInput(true);
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
        JSONObject jobj = jsonArray.getJSONObject(0);
        User rueckgabe = null;

        if(jobj != null){
            boolean admin;
            if(jobj.getInt("admin") == 1){
                admin = true;
            }else { admin = false;}

            rueckgabe = new User(jobj.getInt("userid"),jobj.getString("titel"),jobj.getString("name"),
                    jobj.getString("vorname"),jobj.getString("geschlecht"),jobj.getString("geburtsdatum"),
                    admin,jobj.getString("email"));
        }
        assertNotSame(rueckgabe, classToTest.getUserByLogin("test@mail.de", "test"));
        assertEquals(rueckgabe, classToTest.getUserByLogin("test@mail.de", "test"));
    }

    /**
     * /TF02/ Fehlerhaftes Einloggen
     * Anwendungsfall
     * /LF01/
     *
     * Schritte
     * Login mit Credentials test / test
     *
     * erwartetes Ergebnis
     * Der Benutzer erhält den entsprechenden Benutzer als Antwort
     *
     * Ergebnis
     * Der Benutzer erhält die Null-Referenz
     */
    @Test
    public void fehlerhaftesEinloggen() throws IOException, JSONException {
        assertNull(classToTest.getUserByLogin("test", "test"));
    }
}
