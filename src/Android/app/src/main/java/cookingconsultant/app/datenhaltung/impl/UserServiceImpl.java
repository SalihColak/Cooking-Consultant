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


    private final String URL_GET_USER_BY_ID="http://10.49.223.166/getBenutzerByID.php";
    private final String URL_GET_USER_BY_LOGIN="http://10.49.223.166/getBenutzerByLogin.php";

    public UserServiceImpl(){

    }

    @Override
    public User getUserByID(Integer userid) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_USER_BY_ID+"?userid="+userid);
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
        User user = null;

        if(jobj != null){
            user = new User(jobj.getInt("userid"),jobj.getString("titel"),jobj.getString("name"),
                    jobj.getString("vorname"),jobj.getString("geschlecht"),jobj.getString("geburtsdatum"),
                    jobj.getBoolean("admin"),jobj.getString("email"));
            EinkaufslisteService einkaufslisteService = new EinkaufslisteServiceImpl();
            user.setEinkaufslisten(einkaufslisteService.getEinkaufslistenByUserID(user.getUserid()));
        }


        return user;
        //return new User(userid,"Herr","Mustermann","Max","maennlich","1997/03/14",true,"max.mustermann@th-koeln.de");
    }

    @Override
    public User getUserByLogin(String email, String password) throws IOException, JSONException {
        URL myurl = new URL(URL_GET_USER_BY_LOGIN+"?email="+email+"&passwort="+password);
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
        User user = null;

        if(jobj != null){
            user = new User(jobj.getInt("userid"),jobj.getString("titel"),jobj.getString("name"),
                    jobj.getString("vorname"),jobj.getString("geschlecht"),jobj.getString("geburtsdatum"),
                    jobj.getBoolean("admin"),jobj.getString("email"));
            EinkaufslisteService einkaufslisteService = new EinkaufslisteServiceImpl();
            user.setEinkaufslisten(einkaufslisteService.getEinkaufslistenByUserID(user.getUserid()));
        }


        return user;
        //return new User(3,"Herr","Mustermann","Max","maennlich","1997/03/14",true,email);
    }
}
