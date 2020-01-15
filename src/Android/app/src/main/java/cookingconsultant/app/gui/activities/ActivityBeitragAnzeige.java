package cookingconsultant.app.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;
import cookingconsultant.app.fachlogik.impl.KochlexikonVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.KochlexikonVerwaltung;

public class ActivityBeitragAnzeige extends AppCompatActivity {

    private int beitid;
    private TextView titel,inhalt;
    private BeitragGrenz beitragGrenz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beitrag_anzeige);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_beitrag_id);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        beitid = getIntent().getIntExtra("beitid",-1);

        inhalt = findViewById(R.id.beitrag_inhalt);
        titel = findViewById(R.id.beitrag_titel_id);
        ScrollView scrollView = findViewById(R.id.scrollview_kochanzeige_id);


        LoadBeitragContent loadBeitragContent = new LoadBeitragContent();
        loadBeitragContent.execute();
    }

    private class LoadBeitragContent extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            KochlexikonVerwaltung kochlexikonVerwaltung = new KochlexikonVerwaltungImpl();
            try {
                beitragGrenz = kochlexikonVerwaltung.getBeitragByID(beitid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            titel.setText(beitragGrenz.getTitel());
            inhalt.setText(beitragGrenz.getInhalt());
        }
    }
}
