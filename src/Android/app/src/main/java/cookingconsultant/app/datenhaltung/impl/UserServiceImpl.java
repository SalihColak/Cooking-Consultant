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

import cookingconsultant.app.datenhaltung.entities.User;
import cookingconsultant.app.datenhaltung.services.UserService;
import cookingconsultant.app.fachlogik.services.Constants;

public class UserServiceImpl implements UserService {


    private final String URL_GET_USER_BY_ID= Constants.IP_SERVER+"getBenutzerByID.php";
    private final String URL_GET_USER_BY_LOGIN=Constants.IP_SERVER+"getBenutzerByEmailPasswort.php";

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
            boolean admin;
            if(jobj.getInt("admin") == 1){
                admin = true;
            }else { admin = false;}
            user = new User(jobj.getInt("userid"),jobj.getString("titel"),jobj.getString("name"),
                    jobj.getString("vorname"),jobj.getString("geschlecht"),jobj.getString("geburtsdatum"),
                    admin,jobj.getString("email"));
        }
        return user;
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
            boolean admin;
            if(jobj.getInt("admin") == 1){
                admin = true;
            }else { admin = false;}

            user = new User(jobj.getInt("userid"),jobj.getString("titel"),jobj.getString("name"),
                    jobj.getString("vorname"),jobj.getString("geschlecht"),jobj.getString("geburtsdatum"),
                    admin,jobj.getString("email"));
        }


        return user;
    }
}
