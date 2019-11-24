package cookingconsultant.app.gui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;

public class ActivityKochvorgang extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset = 0;
    private boolean running = false;
    private ProgressBar progressBar;
    private int progress = 1;
    private int maxSchritte = -1;
    private TextView schritt;
    private int rezid;
    private RezeptGrenz rezeptGrenz;
    private List<String> schritte;
    private TextView schrittText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_kochvorgang);

        rezid = getIntent().getIntExtra("rezid",-1);

        LoadRezeptGrenz loadRezeptGrenz = new LoadRezeptGrenz();
        loadRezeptGrenz.execute();


        schrittText = findViewById(R.id.schritt_id);
        schritt = findViewById(R.id.schritt_counter);
        chronometer = findViewById(R.id.chronometer_id);
        progressBar = findViewById(R.id.progressBar);
    }


    public void startTimer(View view) {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }


    }

    public void stopTimer(View view) {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void exit(View view){
        finish();
    }

    public void weiter(View view){
        if(progress<maxSchritte) {
            progress++;
        }

        progressBar.setProgress(progress);
        schritt.setText(progress+". Schritt");
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }

        schrittText.setText(schritte.get(progress-1));
    }

    public void zurueck(View view){
        if(progress>1) {
            progress--;
        }
        progressBar.setProgress(progress);
        schritt.setText(progress+". Schritt");
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
        schrittText.setText(schritte.get(progress-1));
    }

    private class LoadRezeptGrenz extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            RezeptVerwaltung rezeptVerwaltung = new RezeptVerwaltungImpl();
            try {
                rezeptGrenz = rezeptVerwaltung.getRezeptByID(rezid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            schritte = rezeptGrenz.getSchritte();
            maxSchritte = schritte.size();
            if(!schritte.isEmpty()){
                schrittText.setText(schritte.get(0));
            }
            progressBar.setMax(maxSchritte);
            progressBar.setProgress(progress);
            return null;
        }
    }
}
