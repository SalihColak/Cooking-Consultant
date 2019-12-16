package cookingconsultant.app.gui.activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cookingconsultant.app.R;
import cookingconsultant.app.gui.fragments.EinkaufFragment;
import cookingconsultant.app.gui.fragments.KochlexikonFragment;
import cookingconsultant.app.gui.fragments.RezeptFragment;
import cookingconsultant.app.gui.adapter.ViewPagerAdapter;
import cookingconsultant.app.gui.receivers.NotificationReceiver;

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

        //if(!notificationSet) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 15);

            Intent intent = new Intent(this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
            SharedPreferences.Editor editor = getSharedPreferences("userData",MODE_PRIVATE).edit();
            editor.putBoolean("notification",true);
            editor.apply();
        //}

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

    public void test(View view){
        Intent intent = new Intent(this, ActivityMaps.class);
        //startActivity(intent);
    }
}
