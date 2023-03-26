package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pefdneves.mydots.notification.NotificationScheduler

class NotificationWorker(
    context: Context,
    private val notificationScheduler: NotificationScheduler,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun onStopped() {
        super.onStopped()
        notificationScheduler.cancelNotification()
    }

    override fun doWork(): Result {
        notificationScheduler.runPeriod()
        return Result.success()
    }
}