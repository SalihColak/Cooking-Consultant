package cookingconsultant.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.adapter.ViewPagerAdapter;

public class ActivityMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
