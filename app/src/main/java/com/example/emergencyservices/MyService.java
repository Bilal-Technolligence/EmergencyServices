package com.example.emergencyservices;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (0<=1){
                    try {
                        Thread.sleep(1000);
                        ShowNotification();


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return START_STICKY;
    }

    private void ShowNotification() {
        Uri alarmSound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channelId");
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title").setPriority(1).setSound(alarmSound).setContentText("This is message").setContentIntent(pendingIntent);
        Notification notification=builder.build();
        startForeground(123, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
    return null;
    }

    @Override
    public void onDestroy() {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
    }
}
