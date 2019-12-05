package cookingconsultant.app.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatGrenz;
import cookingconsultant.app.fachlogik.grenz.ZutatStateGrenz;
import cookingconsultant.app.gui.adapter.EinkaufslisteZutatenAdapter;
import cookingconsultant.app.gui.adapter.RezeptZutatenAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;

public class ActivityEinkaufslisteAnzeige extends AppCompatActivity implements OnNoteListener {

    private EinkaufslisteGrenz einkaufslisteGrenz;
    private RecyclerView recyclerView;
    private EinkaufslisteZutatenAdapter einkaufslisteZutatenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste_anzeige);

        einkaufslisteGrenz = (EinkaufslisteGrenz) getIntent().getSerializableExtra("einkaufsliste");

        TextView rezeptname = findViewById(R.id.einkaufsliste_rezept_id);
        if(einkaufslisteGrenz.getPortion() != 1) {
            rezeptname.setText(einkaufslisteGrenz.getRezept().getName() + " " + einkaufslisteGrenz.getPortion() + " Portionen");
        }
        else{
            rezeptname.setText(einkaufslisteGrenz.getRezept().getName() + " eine Portion");
        }
        String mengeArray[] = einkaufslisteGrenz.getRezept().getMenge().split(";");
        List<String> mengeListOhnePortion = Arrays.asList(mengeArray);

        List<String> mengeList = new ArrayList<>();
        for(String menge : mengeListOhnePortion){
            int mengeInt = Integer.parseInt(menge) * einkaufslisteGrenz.getPortion();
            mengeList.add(""+mengeInt);
        }

        recyclerView = findViewById(R.id.rv_einkaufsliste_zutaten);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        einkaufslisteZutatenAdapter = new EinkaufslisteZutatenAdapter(einkaufslisteGrenz,mengeList,this,this);
        recyclerView.setAdapter(einkaufslisteZutatenAdapter);
    }

    public void finish(View view) {
        finish();
    }

    @Override
    public void onNoteClick(int position) {
        ZutatStateGrenz zutatStateGrenz = einkaufslisteGrenz.getZutatStateList().get(position);
        Toast.makeText(this,zutatStateGrenz.getZutatGrenz().getName()+" "+zutatStateGrenz.isStatus(),Toast.LENGTH_SHORT).show();

    }

    public void startMap(View view){
        Intent intent = new Intent(this,ActivityMaps.class);
        startActivity(intent);
    }
}
