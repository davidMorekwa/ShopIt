package com.example.shopit.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shopit.R

const val NOTIFICATION_CHANNEL_ID = "SYNC_NOTIFICATION"
const val NOTIFICATION_CHANNEL_NAME = "Remote-Local Data Sync"
const val NOTIFICATION_CONTENT_TITLE = "Data sync"

@RequiresApi(Build.VERSION_CODES.O)
fun showSyncNotification(context: Context, message: String){
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
    val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
    notificationManager?.createNotificationChannel(notificationChannel)

    val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(NOTIFICATION_CONTENT_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSmallIcon(R.drawable.ic_launcher_foreground)

    NotificationManagerCompat.from(context).notify(1, notificationBuilder.build())
}