package cookingconsultant.app.datenhaltung.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.datenhaltung.entities.Beitrag;
import cookingconsultant.app.datenhaltung.services.KochlexikonService;
import cookingconsultant.app.fachlogik.services.Constants;

public class KochlexikonServiceImpl implements KochlexikonService {
    private static final String URL_GET_BEITRAG_BY_ID = Constants.IP_SERVER+"getBeitragByID.php";
    private static final String URL_GET_BEITRAG = Constants.IP_SERVER+"getBeitrag.php";
    private static final String URL_GET_BEITRAEGE_BY_KATEGORIE = Constants.IP_SERVER+"getBeitraegeByKategorie.php";

    @Override
    public Beitrag getBeitragByID(Integer beitid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_BEITRAG_BY_ID+"?beitid="+beitid);
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
        Beitrag beitrag = null;
        if(jobj!=null) {
            beitrag = new Beitrag(jobj.getInt("beitid"),jobj.getString("titel"),jobj.getString("inhalt"),jobj.getString("kategorie"));
        }
        return beitrag;
    }

    @Override
    public List<Beitrag> getBeitraegeByKategorie(String kategorie) throws IOException, JSONException{
        URL myurl = new URL(URL_GET_BEITRAEGE_BY_KATEGORIE+"?kategorie="+kategorie);
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
        List<Beitrag> beitragList = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            jobj = jsonArray.getJSONObject(i);
            if(jobj != null){
                Beitrag beitrag = new Beitrag(jobj.getInt("beitid"),jobj.getString("titel"),jobj.getString("inhalt"), jobj.getString("kategorie"));
                beitragList.add(beitrag);
            }
        }
        return beitragList;
    }
    @Override
    public List<Beitrag> getBeitraege() throws IOException, JSONException{
        URL myurl = new URL(URL_GET_BEITRAG);
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
        List<Beitrag> beitragList = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            jobj = jsonArray.getJSONObject(i);
            if(jobj != null){
                Beitrag beitrag = new Beitrag(jobj.getInt("beitid"),jobj.getString("titel"),jobj.getString("inhalt"), jobj.getString("kategorie"));
                beitragList.add(beitrag);
            }
        }
        return beitragList;
    }
}
