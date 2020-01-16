package cookingconsultant.app.gui.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cookingconsultant.app.R;
import cookingconsultant.app.fachlogik.grenz.RezeptGrenz;
import cookingconsultant.app.fachlogik.impl.RezeptVerwaltungImpl;
import cookingconsultant.app.fachlogik.services.RezeptVerwaltung;
import cookingconsultant.app.gui.activities.ActivityMain;

public class NotifyWorker extends Worker {
    private Context context;
    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

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
            switch (random) {
                case 0:   content = "Es ist wieder mal Zeit " + rezeptGrenzList.get(random).getName().toLowerCase() + " zu kochen!";
                    break;
                case 1: content = "Na mal wieder Lust auf was "+rezeptGrenzList.get(random).getPraeferenz().toLowerCase()+"es?";
                        break;
                case 2: content = "Mal wieder Besuch da? Koche jetzt etwas "+rezeptGrenzList.get(random).getPraeferenz().toLowerCase()+"es!";
                                break;
                default: content = "Entdecke neue leckere Rezepte nach deinen Wünschen!";
            }
        }
        else {
            content = "Lust mal wieder etwas einzigartiges zu kochen?";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"100")
                .setSmallIcon(R.mipmap.applogo)
                .setContentTitle("Hallo "+vorname)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());

        return Result.success();
    }
}
