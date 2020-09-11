package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.pefdneves.mydots.domain.usecase.NotificationUseCase
import com.pefdneves.mydots.utils.notification.DotsNotificationManager

class NotificationWorkerFactory(private val notificationUseCase: NotificationUseCase, private val dotsNotificationManager: DotsNotificationManager) : WorkerFactory(){

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return NotificationWorker(appContext, workerParameters,notificationUseCase,dotsNotificationManager)
    }

}