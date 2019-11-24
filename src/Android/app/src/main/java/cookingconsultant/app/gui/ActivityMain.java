package cookingconsultant.app.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.EinkaufslisteGrenz;
import cookingconsultant.app.fachlogik.impl.EinkaufslisteVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.EinkaufslisteVerwaltung;
import cookingconsultant.app.gui.adapter.EinkaufslisteAdapter;
import cookingconsultant.app.gui.adapter.ViewPagerAdapter;
import cookingconsultant.app.gui.services.OnNoteListener;

public class ActivityMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int userid;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  ENDVERSION

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userid",-1);
        if(userid == -1){
            Intent intent = new Intent(this,LoginActivtiy.class);
            startActivity(intent);
            finish();
        }*/

        userid = 1; //prototyp

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new RezeptFragment(),"Suche");
        adapter.addFragment(new EinkaufFragment(),"Liste");
        adapter.addFragment(new KochlexikonFragment(),"Lexikon");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void searchRezepte(View view){
        Intent intent = new Intent(this,ActivityRezept.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() != 0){
            viewPager.setCurrentItem(0);
        }
        else{
            super.onBackPressed();
        }

    }
}
