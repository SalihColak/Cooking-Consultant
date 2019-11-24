package cookingconsultant.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_rezepte_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);


        rezeptVerwaltung = new RezeptVerwaltungImpl();

        try {
            rezeptList = rezeptVerwaltung.getRezeptByQuery("blabla suche");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RezeptAdapter rezeptAdapter = new RezeptAdapter(this,rezeptList,this);
        recyclerView.setAdapter(rezeptAdapter);


    }

    @Override
    public void onNoteClick(int position) {
        RezeptGrenz rezeptGrenz = rezeptList.get(position);
        Intent intent = new Intent(this,ActivityRezeptAnzeige.class);
        intent.putExtra("rezid",rezeptGrenz.getRezid());
        startActivity(intent);
    }
}
