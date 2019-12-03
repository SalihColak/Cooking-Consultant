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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.ZutatService;
import cookingconsultant.app.fachlogik.services.Constants;

public class ZutatServiceImpl implements ZutatService {

    private final String URL_GET_ZUTAT_BY_ID = Constants.IP_SERVER+"getZutatByID.php";
    private final String URL_GET_ZUTAT_BY_REZEPT_ID = Constants.IP_SERVER+"getZutatByRezeptID.php";

    @Override
    public Zutat getZutatByID(Integer zutid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_ZUTAT_BY_ID+"?zutid="+zutid);
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
        Zutat zutat = null;
        if(jobj!=null) {
            zutat = new Zutat(jobj.getInt("zutid"),jobj.getString("name"),jobj.getString("einheit"),jobj.getString("bild"));
        }
        return zutat;
    }

    @Override
    public List<Zutat> getZutatenByRezeptID(Integer rezid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_ZUTAT_BY_REZEPT_ID+"?rezid="+rezid);
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
        JSONObject jobj;
        List<Zutat> zutatList = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++){
            jobj = jsonArray.getJSONObject(i);
            if(jobj!=null){
                zutatList.add(new Zutat(jobj.getInt("zutid"),jobj.getString("name"),jobj.getString("einheit"),jobj.getString("bild")));
            }
        }
        return zutatList;
    }
}
