package cookingconsultant.app.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.grenz.UserGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.impl.UserVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.Constants;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;
import cookingconsultant.app.fachlogik.services.UserVerwaltung;
import cookingconsultant.app.gui.adapter.RezeptZutatenAdapter;
import cookingconsultant.app.gui.fragments.EinkaufFragment;

public class ActivityRezeptAnzeige extends AppCompatActivity {

    private int rezid;
    private RezeptGrenz rezeptGrenz;
    private List<String> mengeList;
    private int portion = 1;
    TextView portionText;
    RecyclerView recyclerView;
    RezeptZutatenAdapter rezeptAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_rezept_anzeige);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rezept_anzeige_id);

        portionText = findViewById(R.id.portion_id);

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

    public void startKochvorgang(View view) throws IOException, JSONException {
        Intent intent = new Intent(this,ActivityKochvorgang.class);
        intent.putExtra("rezid",rezeptGrenz.getRezid());
        InsertBenutzer2Rezept insertBenutzer2Rezept = new InsertBenutzer2Rezept();
        insertBenutzer2Rezept.execute();
        startActivity(intent);
    }

    public void addPortion(View view) {
        if(portion<50){
            portion++;
            portionText.setText(portion+" Portionen");
            setMenge();
        }
    }

    public void removePortion(View view) {
        if (portion>1){
            portion--;
            if(portion == 1){
                portionText.setText(portion+" Portion");
            }
            else{
                portionText.setText(portion+" Portionen");
            }
            setMenge();
        }
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
            String mengeArray[] = rezeptGrenz.getMenge().split(";");
            mengeList = Arrays.asList(mengeArray);

            ImageView bild = (ImageView) findViewById(R.id.rezept_image_id);
            Picasso.get().load(Constants.IP_SERVER+rezeptGrenz.getBild()).into(bild);

            recyclerView = findViewById(R.id.rv_zutaten);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager layoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(layoutManager);
            rezeptAdapter = new RezeptZutatenAdapter(mengeList,rezeptGrenz.getZutaten(),getApplicationContext());
            recyclerView.setAdapter(rezeptAdapter);
        }
    }

    private void setMenge() {
        List<String> mengeListNew = new ArrayList<>();
        for(String menge : mengeList){
            mengeListNew.add("" + portion * Integer.parseInt(menge));
        }
        rezeptAdapter.setMengeList(mengeListNew);
        rezeptAdapter.notifyDataSetChanged();
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
                SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                int userid = sharedPreferences.getInt("userid",-1);
                userGrenz = userVerwaltung.getUserByID(userid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            EinkaufslisteVerwaltung einkaufslisteVerwaltung = new EinkaufslisteVerwaltungImpl();
            try {
                if(userGrenz != null && einkaufslisteVerwaltung.addEinkaufsliste(new EinkaufslisteGrenz(null,"Bearbeitung",userGrenz,rezeptGrenz,portion))){
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
                //Toast.makeText(ActivityRezeptAnzeige.this,"Erfolgreich hinzugefügt",Toast.LENGTH_SHORT).show();
                Snackbar.make(recyclerView,"Rezept wurde der Einkaufsliste hinzugefügt",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private class InsertBenutzer2Rezept extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            int userid = getSharedPreferences("userData",MODE_PRIVATE).getInt("userid",-1);
            RezeptVerwaltung rezeptVerwaltung = new RezeptVerwaltungImpl();
            try {
                rezeptVerwaltung.insertRezeptForUser(userid,rezeptGrenz.getRezid());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
