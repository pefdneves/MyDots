package com.pefdneves.mydots.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.pefdneves.mydots.R
import com.pefdneves.mydots.view.activity.SplashActivity

class DotsNotificationManagerImpl(private val context: Context) : DotsNotificationManager {

    private var notificationManager: NotificationManager? = null

    init {
        createChannel()
    }

    private fun createChannel() {
        val notificationChannel =
            NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
        getNotificationManager()?.createNotificationChannel(notificationChannel)
    }

    private fun getNotificationManager(): NotificationManager? {
        if (notificationManager == null) {
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager?
        }
        return notificationManager
    }

    private fun getPendingIntent() : PendingIntent{
        val intent = Intent(context, SplashActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, FLAG_UPDATE_CURRENT)
    }

    override fun getDefaultForegroundNotification(): Notification {
        val notificationBuilder : Notification.Builder = Notification.Builder(context, context.getString(R.string.notification_channel_id))
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notification_loading))
            .setSmallIcon(R.drawable.app_icon_white_transparent)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentIntent(getPendingIntent())
        return notificationBuilder.build()
    }

    override fun showNotification(title: String, message: String, largeIcon: Bitmap) {
            val notificationBuilder : Notification.Builder = Notification.Builder(context, context.getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.app_icon_white_transparent)
                .setLargeIcon(largeIcon)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(getPendingIntent())
            notificationManager?.notify(DEFAULT_NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun cancelNotification() {
        notificationManager?.cancel(DEFAULT_NOTIFICATION_ID)
    }

    override fun startNotificationService() {
        //TODO: MyDotsService.startService(context)
    }

    override fun stopNotificationService() {
        //TODO: MyDotsService.stopService(context)
    }

    companion object {
        const val DEFAULT_NOTIFICATION_ID = 888
    }
}