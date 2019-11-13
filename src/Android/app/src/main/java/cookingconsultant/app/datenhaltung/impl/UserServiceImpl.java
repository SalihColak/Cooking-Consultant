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
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;

import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.services.EinkaufslisteService;
import cookingconsultant.app.datenhaltung.services.UserService;

public class UserServiceImpl implements UserService {


    private final String URL_GET_USER_BY_ID="";
    private final String URL_GET_USER_BY_LOGIN="";

    public UserServiceImpl(){

    }

    @Override
    public User getUserByID(Integer userid) throws IOException, JSONException {
        URL myurl = null;
        myurl = new URL(URL_GET_USER_BY_ID);
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.connect();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        String postData = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(""+userid, "UTF-8");
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

        //JSONArray jsonArray = new JSONArray(data);
        JSONObject jobj = new JSONObject(data);
        User user = new User(jobj.getInt("userid"));
        user.setTitel(jobj.getString("titel"));
        user.setName(jobj.getString("name"));
        user.setVorname(jobj.getString("vorname"));
        user.setGeschlecht(jobj.getString("geschlecht"));
        user.setGeburtsdatum(jobj.getString("geburtsdatum"));
        user.setEmail(jobj.getString("email"));
        EinkaufslisteService einkaufslisteService = new EinkaufslisteServiceImpl();
        user.setEinkaufslisten(einkaufslisteService.getEinkaufslistenByUserID(user.getUserid()));
        user.setAdmin(jobj.getBoolean("admin"));
        return user;
    }

    @Override
    public User getUserByLogin(String email, String password) throws IOException, JSONException {
        URL myurl = null;
        myurl = new URL(URL_GET_USER_BY_LOGIN);
        HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
        httpURLConnection.connect();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        String postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(""+email, "UTF-8") + "&"
                + URLEncoder.encode("passwort", "UTF-8") + "=" + URLEncoder.encode(""+password, "UTF-8");
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

        JSONObject jobj = new JSONObject(data);
        User user = new User(jobj.getInt("userid"));
        user.setTitel(jobj.getString("titel"));
        user.setName(jobj.getString("name"));
        user.setVorname(jobj.getString("vorname"));
        user.setGeschlecht(jobj.getString("geschlecht"));
        user.setGeburtsdatum(jobj.getString("geburtsdatum"));
        user.setEmail(jobj.getString("email"));
        EinkaufslisteService einkaufslisteService = new EinkaufslisteServiceImpl();
        user.setEinkaufslisten(einkaufslisteService.getEinkaufslistenByUserID(user.getUserid()));
        user.setAdmin(jobj.getBoolean("admin"));
        return user;
    }
}
