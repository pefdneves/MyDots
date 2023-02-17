package com.pefdneves.mydots.worker

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pefdneves.mydots.R
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.usecase.NotificationUseCase
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.TimeUtils
import com.pefdneves.mydots.utils.hasBluetoothPermissions
import com.pefdneves.mydots.utils.notification.DotsNotificationManager

class NotificationWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val notificationUseCase: NotificationUseCase,
    private val dotsNotificationManager: DotsNotificationManager,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : Worker(context, workerParameters) {

    override fun onStopped() {
        super.onStopped()
        dotsNotificationManager.cancelNotification()
    }

    override fun doWork(): Result {
        if (!hasBluetoothPermissions(context)) {
            dotsNotificationManager.showPermissionsMissingNotification()
        }
        notificationUseCase.getBluetoothDevice()?.let {
            showNotification(it, context)
        }
        return Result.success()
    }

    private fun showNotification(
        bluetoothDevice: BluetoothDevice,
        context: Context?
    ) {
        val deviceName: String =
            sharedPreferencesRepository.getRegisteredModel()?.model ?: bluetoothDevice.name
        val batteryLevel = notificationUseCase.getBatteryLevel(bluetoothDevice)
        val batteryInMinutes = notificationUseCase.getBatteryTime(batteryLevel)
        val message = context?.getString(
            R.string.notification_device_battery,
            batteryLevel,
            TimeUtils.getHoursAndMinutesFromMinutesReadable(batteryInMinutes, context)
        )
        context?.let {
            val bitmap = ImageUtils.getBitmapFromDrawable(
                context,
                notificationUseCase.getImageResourceFromModel()
            )
            if (bitmap != null && message != null) {
                dotsNotificationManager.showNotification(
                    deviceName,
                    message,
                    bitmap
                )
            }
        }
    }

    companion object {
        const val WORKER_PERIOD_MINUTES = 15L
        const val PERIODIC_WORKER_ID = "NOTIFICATION_WORKER"
    }
}