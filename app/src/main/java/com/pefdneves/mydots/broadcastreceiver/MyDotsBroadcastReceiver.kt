package com.pefdneves.mydots.broadcastreceiver

import android.content.Context
import android.content.Intent
import com.pefdneves.mydots.notification.NotificationScheduler
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class MyDotsBroadcastReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var dotsNotificationManager: DotsNotificationManager

    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        notificationScheduler.runPeriod()
        dotsNotificationManager.startNotificationService()
    }
}