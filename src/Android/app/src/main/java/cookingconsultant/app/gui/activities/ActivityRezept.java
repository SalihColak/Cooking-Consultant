package cookingconsultant.app.gui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;
import cookingconsultant.app.gui.adapter.RezeptAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;


public class ActivityRezept extends AppCompatActivity implements OnNoteListener {

    List<RezeptGrenz> rezeptList;
    private RezeptVerwaltung rezeptVerwaltung;
    private String query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept);

        query = getIntent().getStringExtra("query");

        LoadRezepte loadRezepte = new LoadRezepte();
        loadRezepte.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class LoadRezepte extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            rezeptVerwaltung = new RezeptVerwaltungImpl();

            try {
                rezeptList = rezeptVerwaltung.getRezeptByQuery(query);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = findViewById(R.id.recycler_view_rezepte_id);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager layoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(layoutManager);
            RezeptAdapter rezeptAdapter = new RezeptAdapter(getApplicationContext(),rezeptList,ActivityRezept.this);
            recyclerView.setAdapter(rezeptAdapter);
        }
    }

    @Override
    public void onNoteClick(int position) {
        RezeptGrenz rezeptGrenz = rezeptList.get(position);
        Intent intent = new Intent(this,ActivityRezeptAnzeige.class);
        intent.putExtra("rezid",rezeptGrenz.getRezid());
        startActivity(intent);
    }
}
