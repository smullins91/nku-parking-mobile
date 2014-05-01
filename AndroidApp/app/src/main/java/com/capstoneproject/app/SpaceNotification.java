package com.capstoneproject.app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

public class SpaceNotification {

    private Notification mNotification;
    private Context mContext;
    private PendingIntent mPending;
    private long mTime;

    public SpaceNotification(Context context, String lot, long time) {

        mContext = context;
        mTime = time;

        Intent intent = new Intent(context, MainActivity.class);
        mPending = PendingIntent.getActivity(context, 0, intent, 0);

        String date = new java.text.SimpleDateFormat("MMM dd hh:mm a").format(new java.util.Date(time));

        Notification.InboxStyle inboxStyle = new Notification.InboxStyle();

        inboxStyle.addLine("Space reserved in " + lot);
        inboxStyle.addLine("until " + date);

        mNotification = new Notification.Builder(context)
                .setContentTitle("NKU Parking")
                .setContentText("Space reserved in " + lot)
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(mPending)
                .setAutoCancel(false)
                .setOngoing(true)
                .setStyle(inboxStyle)
                //.addAction(0, "Open", pIntent)
                .build();
                //.addAction(R.drawable.icon, "Call", pIntent)
                //.addAction(R.drawable.icon, "More", pIntent)
                //.addAction(R.drawable.icon, "And more", pIntent).build();
    }

    public void show() {

        Intent intent = new Intent(mContext, NotificationReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager)(mContext.getSystemService(Context.ALARM_SERVICE));
        alarm.set(AlarmManager.RTC_WAKEUP, mTime, pending);

        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotification);

    }

    public static boolean isVisible(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pending != null;
    }


}
