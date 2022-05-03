package com.example.mobil.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {
    private  NotificationHandler notificationHelper;

    @Override
    public boolean onStartJob(JobParameters params) {
        notificationHelper = new NotificationHandler(getApplicationContext());
        notificationHelper.send("Ideje felvenned egy kurzust");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}