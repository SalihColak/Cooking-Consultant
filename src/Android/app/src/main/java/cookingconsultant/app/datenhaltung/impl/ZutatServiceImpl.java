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
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.ZutatService;

public class ZutatServiceImpl implements ZutatService {

    private final String URL_GET_ZUTAT_BY_ID = "http://10.49.223.166/getZutatByID.php";

    @Override
    public Zutat getZutatByID(Integer zutid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_ZUTAT_BY_ID);
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.connect();

        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        String postData = URLEncoder.encode("zutid", "UTF-8") + "=" + URLEncoder.encode(""+zutid, "UTF-8");
        bw.write(postData);
        bw.flush();
        os.close();


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
        RezeptService rezeptService = new RezeptServiceImpl();
        return rezeptService.getRezeptByID(rezid).getZutaten();
    }
}
