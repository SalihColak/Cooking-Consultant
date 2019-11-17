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


public class ActivityRezept extends AppCompatActivity implements RezeptAdapter.OnNoteListerner {

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

        /*rezeptList = new ArrayList<>();


        RezeptGrenz rezeptGrenz = new RezeptGrenz(2);
        rezeptGrenz.setName("Asiatische Nudeln");
        rezeptGrenz.setKochzeit("15min");
        rezeptGrenz.setBild(""+R.drawable.nudel);
        RezeptGrenz rezeptGrenz1 = new RezeptGrenz(3);
        rezeptGrenz1.setName("Lahmacun - scharf");
        rezeptGrenz1.setKochzeit("30min");
        rezeptGrenz1.setBild(""+R.drawable.abendessen);
        RezeptGrenz rezeptGrenz2 = new RezeptGrenz(2);
        rezeptGrenz2.setName("Steak nach Spanischer Art");
        rezeptGrenz2.setKochzeit("1h 30min");
        rezeptGrenz2.setBild(""+R.drawable.mittagessen);
        RezeptGrenz rezeptGrenz3 = new RezeptGrenz(2);
        rezeptGrenz3.setName("Burger spezial - mit Jallapenos");
        rezeptGrenz3.setKochzeit("45min");
        rezeptGrenz3.setBild(""+R.drawable.snack);
        rezeptList.add(rezeptGrenz);
        rezeptList.add(rezeptGrenz1);
        rezeptList.add(rezeptGrenz2);
        rezeptList.add(rezeptGrenz3);*/

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
