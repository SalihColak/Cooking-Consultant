package cookingconsultant.app.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.impl.UserVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.UserVerwaltung;

public class ActivityLogin extends AppCompatActivity {

    private EditText emailEdit;
    private EditText passwortEdit;
    private TextView error;
    private String email, passwort;
    private UserGrenz userGrenz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.email);
        passwortEdit = findViewById(R.id.passwort);
        error = findViewById(R.id.error_meld_login);
    }

    public void login(View view) {
        email = emailEdit.getText().toString();
        passwort = passwortEdit.getText().toString();

        if(passwort.equals("") || email.equals("")){
            error.setTextColor(error.getResources().getColor(R.color.card_background0));
            error.setTextSize(18f);
            error.setText("Bitte Email und Passwort eingeben");
            return;
        }


        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        DoLogin doLogin = new DoLogin();
        doLogin.execute();


    }

    private class DoLogin extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            UserVerwaltung userVerwaltung = new UserVerwaltungImpl();
            try {
                userGrenz = userVerwaltung.getUserByLogin(email,passwort);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(userGrenz!=null){
                SharedPreferences sharedPreferences = getSharedPreferences("userData",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userid",userGrenz.getUserid());
                editor.putString("uservorname",userGrenz.getVorname());
                editor.putString("username",userGrenz.getName());
                editor.apply();
                Intent intent = new Intent(ActivityLogin.this,ActivityMain.class);
                startActivity(intent);
                finish();
            }else{
                error.setTextColor(error.getResources().getColor(R.color.card_background0));
                error.setTextSize(18f);
                error.setText("Email und Passwort stimmen nicht überein");
            }
        }
    }
}
