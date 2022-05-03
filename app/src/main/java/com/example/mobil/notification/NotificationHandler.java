package com.example.mobil.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.mobil.AllCoursesActivity;
import com.example.mobil.HomeActivity;
import com.example.mobil.R;


public class NotificationHandler {
    private static final String CHANNEL_ID = "courses";
    private final int NOTIFICATION_ID = 0;

    private final NotificationManager notifyManager;
    private final Context context;

    public NotificationHandler(Context context) {
        this.context = context;
        this.notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel
                (CHANNEL_ID, "Kurzusok", NotificationManager.IMPORTANCE_HIGH);

        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setDescription("Értesítések a kurzusaidról");

        notifyManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        Intent intent = new Intent(context, HomeActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Kurzusok")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_book_24)
                .setContentIntent(pendingIntent);

        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}