package com.pefdneves.mydots.service

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.pefdneves.mydots.domain.usecase.NotificationUseCase
import com.pefdneves.mydots.utils.hasBluetoothPermissions
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import com.pefdneves.mydots.worker.NotificationWorker
import dagger.android.DaggerService
import java.time.Duration
import javax.inject.Inject

class MyDotsService : DaggerService() {

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var myDotsNotificationManager: DotsNotificationManager

    private var workRequest: PeriodicWorkRequest? = null
    private var workManager: WorkManager? = null

    private fun startForegroundService() {
        if (isAndroidQOrAbove()) {
            notificationUseCase.getDefaultForegroundNotification()?.also { notification ->
                startForeground(
                    DotsNotificationManagerImpl.DEFAULT_NOTIFICATION_ID,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
                )
            }
        } else {
            startForeground(
                DotsNotificationManagerImpl.DEFAULT_NOTIFICATION_ID,
                notificationUseCase.getDefaultForegroundNotification()
            )
        }
    }

    private fun isAndroidQOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        if (!notificationUseCase.isNotificationEnabled()) {
            stopSelf()
        } else {
            waitForBluetoothToUpdateBattery()
            workManager = WorkManager.getInstance(this)
            if (!hasBluetoothPermissions(applicationContext)) {
                showAllowPermissionsNotification()
            } else {
                if (notificationUseCase.isDeviceConnected()) {
                    if (notificationUseCase.isRegisteredDevice()) {
                        scheduleWorker()
                    } else {
                        stopSelf()
                    }
                } else {
                    stopSelf()
                }
            }
        }
        return Service.START_STICKY
    }

    private fun showAllowPermissionsNotification() {
        if (isAndroidQOrAbove()) {
            notificationUseCase.getDefaultMissingPermissionsNotification()?.also { notification ->
                startForeground(
                    DotsNotificationManagerImpl.DEFAULT_NOTIFICATION_ID,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
                )
            }
        } else {
            startForeground(
                DotsNotificationManagerImpl.DEFAULT_NOTIFICATION_ID,
                notificationUseCase.getDefaultMissingPermissionsNotification()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!notificationUseCase.isDeviceConnected()) {
            notificationUseCase.cancelNotification()
            workManager?.cancelAllWorkByTag(NotificationWorker.javaClass.simpleName)
        } else {
            restartByAlarm()
            restartByIntent()
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        restartByAlarm()
        restartByIntent()
    }

    private fun restartByIntent() {
        sendBroadcast(Intent(INTENT_SERVICE_RESTART))
    }

    private fun restartByAlarm() {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        val restartServicePendingIntent = PendingIntent.getService(
            applicationContext,
            1,
            restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmService =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService[AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + DELAY_RESTART_SERVICE] =
            restartServicePendingIntent
    }

    private fun waitForBluetoothToUpdateBattery() {
        Thread.sleep(DELAY_BLUETOOTH)
    }

    private fun scheduleWorker() {
        workRequest =
            PeriodicWorkRequest.Builder(
                NotificationWorker::class.java,
                Duration.ofSeconds(NotificationWorker.WORKER_PERIOD_MINUTES)
            )
                .addTag(NotificationWorker.javaClass.simpleName)
                .build()
        workRequest?.let {
            workManager?.enqueueUniquePeriodicWork(
                NotificationWorker.PERIODIC_WORKER_ID, ExistingPeriodicWorkPolicy.REPLACE,
                it
            )
        }
    }

    companion object {
        const val DELAY_BLUETOOTH = 1000L
        const val DELAY_RESTART_SERVICE = 1000L
        const val INTENT_SERVICE_RESTART = "com.pefdneves.mydots.ACTION_SERVICE_RESTART"

        fun startService(context: Context) {
            val startIntent = Intent(context, MyDotsService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, MyDotsService::class.java)
            context.stopService(stopIntent)
        }
    }

}