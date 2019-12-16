package cookingconsultant.app.datenhaltung.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.ZutatService;
import cookingconsultant.app.fachlogik.services.Constants;

public class RezeptServiceImpl implements RezeptService {

    private static final String URL_GET_REZEPT_BY_ID = Constants.IP_SERVER+"getRezeptByID.php";
    private static final String URL_GET_REZEPTE_BY_QUERY = Constants.IP_SERVER+"getRezeptByArtAnlassPraeferenz.php";
    private static final String URL_GET_REZEPTE_BY_USER_ID = Constants.IP_SERVER+"getBenutzer2Rezept.php";
    private static final String URL_INSERT_REZEPT_FOR_USER = Constants.IP_SERVER+"insertBenutzer2Rezept.php";;

    @Override
    public Rezept getRezeptByID(Integer rezid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_REZEPT_BY_ID+"?rezid="+rezid);
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
        JSONObject jobj = jsonArray.getJSONObject(0);


        List<String> schritte;
        schritte = Arrays.asList(jobj.getString("schritte").split(";"));

        Rezept rezept = null;
        if(jobj!=null) {
            rezept = new Rezept(jobj.getInt("rezid"), jobj.getString("name"), jobj.getString("beschreibung"), schritte,
                    jobj.getString("art"), jobj.getString("anlass"), jobj.getString("praeferenz"), jobj.getString("kochzeit") + "min",
                    jobj.getString("bild"), jobj.getString("menge"));
            ZutatService zutatService = new ZutatServiceImpl();
            List<Zutat> zutatList = zutatService.getZutatenByRezeptID(rezid);
            rezept.setZutaten(zutatList);
        }

        return rezept;
    }


    @Override
    public List<Rezept> getRezepteByQuery(String query) throws IOException, JSONException {

        String queryArray[] = query.split(";");

        String art = queryArray[0];
        String anlass = queryArray[1];
        String praeferenz = queryArray[2];

        URL myurl = new URL(URL_GET_REZEPTE_BY_QUERY+"?art="+art+"&anlass="+anlass+"&praeferenz="+praeferenz);
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
        List<Rezept> list = new ArrayList<>();
        for (int i = 0; i<jsonArray.length();i++){
            jobj = jsonArray.getJSONObject(i);
            List<String> schritte;
            schritte = Arrays.asList(jobj.getString("schritte").split(";"));
            list.add(new Rezept(jobj.getInt("rezid"), jobj.getString("name"), jobj.getString("beschreibung"), schritte,
                    jobj.getString("art"), jobj.getString("anlass"), jobj.getString("praeferenz"), jobj.getString("kochzeit") + "min",
                    jobj.getString("bild"), jobj.getString("menge")));
        }
        return list;

        /*List<Rezept> list = new ArrayList<>();
        list.add(getRezeptByID(1));
        list.add(getRezeptByID(1));
        return list;*/
    }

    @Override
    public List<Rezept> getRezepteByUserID(Integer userid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_REZEPTE_BY_USER_ID+"?userid="+userid);
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
        List<Integer> rezids = new ArrayList<>();
        List<Rezept> list = new ArrayList<>();
        for (int i = 0; i<jsonArray.length();i++){
            jobj = jsonArray.getJSONObject(i);
            rezids.add(jobj.getInt("rezid"));
        }

        for(int j = 0; j<rezids.size(); j++){
            list.add(getRezeptByID(rezids.get(j)));
        }

        return list;
    }

    @Override
    public void insertRezeptForUser(Integer rezid, Integer userid) throws IOException, JSONException {
        URL myurl = new URL(URL_INSERT_REZEPT_FOR_USER+"?userid="+userid+"&rezid="+rezid);
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
    }
}
