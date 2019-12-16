package cookingconsultant.app.gui.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;
import cookingconsultant.app.gui.activities.ActivityMain;

public class NotificationReceiver extends BroadcastReceiver {

    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        LoadUserRezepte loadUserRezepte = new LoadUserRezepte();
        loadUserRezepte.execute();
    }

    private class LoadUserRezepte extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent repeatingIntent = new Intent(context, ActivityMain.class);
            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            String vorname = context.getSharedPreferences("userData",Context.MODE_PRIVATE).getString("uservorname","NA");
            int userid =  context.getSharedPreferences("userData",Context.MODE_PRIVATE).getInt("userid",-1);

            RezeptVerwaltung rezeptVerwaltung = new RezeptVerwaltungImpl();
            List<RezeptGrenz> rezeptGrenzList = null;
            try {
                rezeptGrenzList = rezeptVerwaltung.getRezeptByUserId(userid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String content = "";

            if (rezeptGrenzList!= null && !rezeptGrenzList.isEmpty()){
                Random r = new Random();
                int random = r.nextInt((rezeptGrenzList.size()-1 ) + 1);
                if(random == rezeptGrenzList.size()) random--;
                content = "Es ist wieder mal Zeit "+rezeptGrenzList.get(random).getPraeferenz().toLowerCase()+" zu kochen!";
            }
            else {
                content = "Es ist wieder mal Zeit zu kochen!";
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"100")
                    .setSmallIcon(R.mipmap.applogo)
                    .setContentTitle("Hallo "+vorname)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notificationManager.notify(100,builder.build());
            return null;
        }
    }
}
