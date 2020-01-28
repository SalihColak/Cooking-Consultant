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

import cookingconsultant.app.datenhaltung.entities.Einkaufsliste;
import cookingconsultant.app.datenhaltung.entities.Rezept;
import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.entities.Zutat;
import cookingconsultant.app.datenhaltung.entities.ZutatState;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;
import cookingconsultant.app.datenhaltung.services.RezeptService;
import cookingconsultant.app.datenhaltung.services.UserService;
import cookingconsultant.app.datenhaltung.services.ZutatService;
import cookingconsultant.app.fachlogik.services.Constants;

public class EinkaufslisteServiceImpl implements EinkaufslisteService {
    private static final String URL_GET_EINKAUFSLISTEN_BY_USER_ID = Constants.IP_SERVER+"getEinkaufslisteByUserID.php";
    private static final String URL_INSERT_EINKAUFSLISTE = Constants.IP_SERVER+"insertEinkaufsliste.php";
    private static final String URL_DELETE_EINKAUFSLISTE = Constants.IP_SERVER+"deleteEinkaufsliste.php";
    private static final String URL_GET_ZUTAT_STATE_LIST_BY_EINK_ID = Constants.IP_SERVER+"getEinkaufsliste2ZutatState.php";;
    private static final String URL_INSERT_ZUTAT_STATE_BY_EINK_ID = Constants.IP_SERVER+"insertEinkaufsliste2ZutatState.php";
    private static final String URL_DELETE_ZUTAT_STATE_BY_EINK_ID = Constants.IP_SERVER+"deleteEinkaufsliste2ZutatState.php";
    private static final String URL_UPDATE_ZUTAT_STATE_BY_EINK_ID = Constants.IP_SERVER+"updateEinkaufsliste2ZutatState.php";
    private static final String URL_UPDATE_EINKAUFSLISTE_ZUSTAND_BY_EINK_ID = Constants.IP_SERVER+"updateEinkaufslisteZustand.php";

    @Override
    public Einkaufsliste getEinkaufslisteByID(Integer einkid) {
        Einkaufsliste einkaufsliste = new Einkaufsliste(einkid,"bearbeitung",3);
        return einkaufsliste;
    }

    @Override
    public List<Einkaufsliste> getEinkaufslistenByUserID(Integer userid) throws IOException, JSONException {

        URL myurl = new URL(URL_GET_EINKAUFSLISTEN_BY_USER_ID+"?userid="+userid);
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
        List<Einkaufsliste> einkaufslisteList = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            jobj = jsonArray.getJSONObject(i);
            if(jobj != null){
                Einkaufsliste einkaufsliste = new Einkaufsliste(jobj.getInt("einkid"),jobj.getString("zustand"),jobj.getInt("menge"));
                UserService userService = new UserServiceImpl();
                User user = userService.getUserByID(jobj.getInt("userid"));
                einkaufsliste.setUser(user);
                RezeptService rezeptService = new RezeptServiceImpl();
                Rezept rezept = rezeptService.getRezeptByID(jobj.getInt("rezid"));
                einkaufsliste.setRezept(rezept);
                einkaufsliste.setZutatStateList(getEinkaufslisteZutatState(jobj.getInt("einkid")));
                einkaufslisteList.add(einkaufsliste);
            }
        }
        return einkaufslisteList;
    }

    @Override
    public boolean deleteEinkaufslisteByID(Integer einkid) throws IOException, JSONException {
        deleteEinkaufslisteZutatState(einkid);
        URL myurl = new URL(URL_DELETE_EINKAUFSLISTE+"?einkid="+einkid);
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
        String check = data.substring(0,2);
        if(check.equals("Er")){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean changeZustandEinkaufslisteByID(Integer einkid, String neuerZustand) throws IOException {
        URL myurl = new URL(URL_UPDATE_EINKAUFSLISTE_ZUSTAND_BY_EINK_ID+"?einkid="+einkid+"&zustand="+neuerZustand);
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
        if(data.substring(0,2).equals("Er")){
            return true;
        }
        return false;
    }

    @Override
    public boolean addEinkaufsliste(Einkaufsliste einkaufsliste) throws IOException, JSONException {
        String zustand = einkaufsliste.getZustand();
        int rezid = einkaufsliste.getRezept().getRezid();
        int userid = einkaufsliste.getUser().getUserid();
        int menge = einkaufsliste.getPortion();
        URL myurl = new URL(URL_INSERT_EINKAUFSLISTE+"?zustand="+zustand+"&userid="+userid+"&rezid="+rezid+"&menge="+menge);
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
        String id = data.substring(0,1);
        if(!id.equals("F")){
            for (int i=0; i<einkaufsliste.getRezept().getZutaten().size();i++){
                insertEinkaufslisteZutatState(Integer.parseInt(data),einkaufsliste.getRezept().getZutaten().get(i).getZutid());
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<ZutatState> getEinkaufslisteZutatState(Integer einkid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_ZUTAT_STATE_LIST_BY_EINK_ID+"?einkid="+einkid);
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
        List<ZutatState> zutatStateList = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){
            jobj = jsonArray.getJSONObject(i);
            if(jobj != null){
                boolean state;
                if(jobj.getInt("state") == 1){
                    state = true;
                }else {
                    state = false;
                }
                ZutatState zutatState = new ZutatState();
                zutatState.setState(state);
                ZutatService zutatService = new ZutatServiceImpl();
                Zutat zutat = zutatService.getZutatByID(jobj.getInt("zutid"));
                zutatState.setZutat(zutat);
                zutatState.setId(jobj.getInt("ein2zutid"));
                zutatStateList.add(zutatState);
            }
        }
        return zutatStateList;
    }

    @Override
    public void insertEinkaufslisteZutatState(Integer einkid, Integer zutid) throws IOException, JSONException {
        URL myurl = new URL(URL_INSERT_ZUTAT_STATE_BY_EINK_ID+"?einkid="+einkid+"&zutid="+zutid+"&state=0");
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
    }

    @Override
    public void deleteEinkaufslisteZutatState(Integer einkid) throws IOException, JSONException {
        URL myurl = new URL(URL_DELETE_ZUTAT_STATE_BY_EINK_ID+"?einkid="+einkid);
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
    }

    @Override
    public void changeZutatState(Integer ein2zutid, boolean state) throws IOException {
        int stateInt = -1;
        if (state) stateInt = 1;
        else stateInt = 0;

        URL myurl = new URL(URL_UPDATE_ZUTAT_STATE_BY_EINK_ID+"?ein2zutid="+ein2zutid+"&state="+stateInt);
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
    }


}
