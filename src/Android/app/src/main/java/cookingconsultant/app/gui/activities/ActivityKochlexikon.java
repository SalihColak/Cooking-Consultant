package cookingconsultant.app.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.BeitragGrenz;
import cookingconsultant.app.fachlogik.impl.KochlexikonVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.KochlexikonVerwaltung;
import cookingconsultant.app.gui.adapter.KochlexikonAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;

public class ActivityKochlexikon extends AppCompatActivity implements OnNoteListener {

    private String kategorie = "";
    private List<BeitragGrenz> beitragGrenzList;
    RecyclerView recyclerView;
    private KochlexikonAdapter kochlexikonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kochlexikon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_kochlex_id);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        beitragGrenzList = new ArrayList<>();

        kategorie = getIntent().getStringExtra("kategorie");



        LoadKochlexikonEntries loadKochlexikonEntries = new LoadKochlexikonEntries();
        loadKochlexikonEntries.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kochlexikonAdapter.filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private class LoadKochlexikonEntries extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            KochlexikonVerwaltung kochlexikonVerwaltung = new KochlexikonVerwaltungImpl();
            try {
                beitragGrenzList = kochlexikonVerwaltung.getBeitraegeByKategorie(kategorie);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView = findViewById(R.id.recycler_view_kochlex_id);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager layoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(layoutManager);
            kochlexikonAdapter = new KochlexikonAdapter(getApplicationContext(),beitragGrenzList,ActivityKochlexikon.this);
            recyclerView.setAdapter(kochlexikonAdapter);
        }
    }

    @Override
    public void onNoteClick(int position) {
        BeitragGrenz beitragGrenz = beitragGrenzList.get(position);
        Intent intent = new Intent(this,ActivityBeitragAnzeige.class);
        intent.putExtra("beitid",beitragGrenz.getBeitid());
        startActivity(intent);
    }
}
