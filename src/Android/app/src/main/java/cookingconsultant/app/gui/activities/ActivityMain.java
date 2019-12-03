package cookingconsultant.app.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.fragments.EinkaufFragment;
import cookingconsultant.app.gui.fragments.KochlexikonFragment;
import cookingconsultant.app.gui.fragments.RezeptFragment;
import cookingconsultant.app.gui.adapter.ViewPagerAdapter;

public class ActivityMain extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int userid;
    private boolean firstUse;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  ENDVERSION*/

        //getSharedPreferences("userData", 0).edit().clear().commit();
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userid",-1);
        firstUse = sharedPreferences.getBoolean("firstUse",true);
        if(firstUse){
            Intent intent = new Intent(this, ActivityFirstStart.class);
            startActivity(intent);
            finish();
        }
        else if(userid == -1){
            Intent intent = new Intent(this,ActivityLogin.class);
            startActivity(intent);
            finish();
        }

        userid = 1; //prototyp

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new RezeptFragment(),"Rezeptsuche");
        adapter.addFragment(new KochlexikonFragment(),"Kochlexikon");
        adapter.addFragment(new EinkaufFragment(),"Einkaufsliste");


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);


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
