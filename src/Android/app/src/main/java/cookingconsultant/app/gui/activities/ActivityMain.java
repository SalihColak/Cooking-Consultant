package cookingconsultant.app.gui.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.fragments.EinkaufFragment;
import cookingconsultant.app.gui.fragments.KochlexikonFragment;
import cookingconsultant.app.gui.fragments.RezeptFragment;
import cookingconsultant.app.gui.adapter.ViewPagerAdapter;
import cookingconsultant.app.gui.receivers.NotifyWorker;

public class ActivityMain extends AppCompatActivity {


    private static final String CHANNEL_ID = "100";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int userid;
    private boolean firstUse;
    private RecyclerView recyclerView;
    private boolean notificationSet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  ENDVERSION*/

        //getSharedPreferences("userData", 0).edit().clear().commit();
        //getSharedPreferences("userData", 0).edit().putBoolean("notification",false).commit();
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        userid = sharedPreferences.getInt("userid",-1);
        notificationSet = sharedPreferences.getBoolean("notification",false);
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

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new RezeptFragment(),"Rezeptsuche");
        adapter.addFragment(new KochlexikonFragment(),"Kochlexikon");
        adapter.addFragment(new EinkaufFragment(),"Einkaufsliste");


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        createNotificationChannel();


        PeriodicWorkRequest saveRequest = new PeriodicWorkRequest.Builder(NotifyWorker.class, 15, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueue(saveRequest);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mychannelname";
            String description = "mychanneldescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

    public void kochutensilien(View view){
        Intent intent = new Intent(this, ActivityKochlexikon.class);
        intent.putExtra("kategorie","kochutensilien");
        startActivity(intent);
    }
    public void warenkunde(View view){
        Intent intent = new Intent(this, ActivityKochlexikon.class);
        intent.putExtra("kategorie","warenkunde");
        startActivity(intent);
    }
    public void kochtechniken(View view){
        Intent intent = new Intent(this, ActivityKochlexikon.class);
        intent.putExtra("kategorie","kochtechniken");
        startActivity(intent);
    }
    public void kochen(View view){
        Intent intent = new Intent(this, ActivityKochlexikon.class);
        intent.putExtra("kategorie","kochen");
        startActivity(intent);
    }
}
