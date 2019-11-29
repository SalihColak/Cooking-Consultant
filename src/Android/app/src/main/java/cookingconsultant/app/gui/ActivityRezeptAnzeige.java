package cookingconsultant.app.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.impl.UserVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;
import cookingconsultant.app.fachlogik.services.UserVerwaltung;

public class ActivityRezeptAnzeige extends AppCompatActivity {

    private int rezid;
    private RezeptGrenz rezeptGrenz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_rezept_anzeige);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rezept_anzeige_id);

        rezid = getIntent().getIntExtra("rezid",-1);
        if(rezid !=-1){
            LoadRezept loadRezept = new LoadRezept();
            try {
                rezeptGrenz = loadRezept.execute(rezid).get();
            } catch (ExecutionException e) {
                rezeptGrenz = null;
                e.printStackTrace();
            } catch (InterruptedException e) {
                rezeptGrenz = null;
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this,"Ein unerwarteter Fehler ist aufgetreten",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    public void startKochvorgang(View view) {
        Intent intent = new Intent(this,ActivityKochvorgang.class);
        intent.putExtra("rezid",rezeptGrenz.getRezid());
        startActivity(intent);
    }


    private class LoadRezept extends AsyncTask<Integer,Void,RezeptGrenz>{

        @Override
        protected RezeptGrenz doInBackground(Integer... ints) {
            RezeptGrenz rezeptGrenz= null;
            RezeptVerwaltung rezeptVerwaltung = new RezeptVerwaltungImpl();
            try {
                rezeptGrenz = rezeptVerwaltung.getRezeptByID(ints[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            TextView name = (TextView) findViewById(R.id.rezept_anzeige_name_id);
            TextView beschreibung = findViewById(R.id.beschreibung_rezept);


            if(rezeptGrenz!=null){

                name.setText(rezeptGrenz.getName());
                beschreibung.setText(rezeptGrenz.getBeschreibung());
            }
            return rezeptGrenz;
        }

        @Override
        protected void onPostExecute(RezeptGrenz rezeptGrenz) {
            ImageView bild = (ImageView) findViewById(R.id.rezept_image_id);
            Picasso.get().load(getString(R.string.ip_server)+"/"+rezeptGrenz.getBild()).into(bild);
        }
    }

    public void finish(View view){
        finish();
    }

    public void addCart(View view) throws IOException, JSONException {
        AddCart addCart = new AddCart();
        addCart.execute();
    }


    private class AddCart extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            UserVerwaltung userVerwaltung = new UserVerwaltungImpl();
            UserGrenz userGrenz = null;
            try {
                userGrenz = userVerwaltung.getUserByID(getIntent().getIntExtra("userid",-1));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();
            try {
                if(einkaufslisteVerwaltung.addEinkaufsliste(new EinkaufslisteGrenz(null,"bearbeitung",rezeptGrenz.getZutaten(),userGrenz,rezeptGrenz))){
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(ActivityRezeptAnzeige.this,"Erfolgreich hinzugefügt",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
