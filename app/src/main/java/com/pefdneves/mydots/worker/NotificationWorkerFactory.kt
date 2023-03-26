package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.pefdneves.mydots.notification.NotificationScheduler

class NotificationWorkerFactory(private val notificationScheduler: NotificationScheduler) : WorkerFactory(){

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return NotificationWorker(appContext, notificationScheduler, workerParameters)
    }

}