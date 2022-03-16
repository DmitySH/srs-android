package bse202.sda.healary.application;

import static bse202.sda.healary.broadcastreceiver.AlarmBroadcastReceiver.TITLE;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class App extends Application {
    public static final String CHANNEL_ID = "HEALARY";

    @Override
    public void onCreate() {
        super.onCreate();
//        createNotificationChannnel();
    }

//    private void createNotificationChannnel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Alarm Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//    }

}
