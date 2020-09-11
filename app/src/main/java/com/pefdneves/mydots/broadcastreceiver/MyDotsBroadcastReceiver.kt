package com.pefdneves.mydots.broadcastreceiver

import android.content.Context
import android.content.Intent
import com.pefdneves.mydots.domain.usecase.NotificationUseCase
import com.pefdneves.mydots.service.MyDotsService
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class MyDotsBroadcastReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        context?.let {
            MyDotsService.startService(context)
        }
    }
}