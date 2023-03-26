package com.pefdneves.mydots.notification

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.pefdneves.mydots.R
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.usecase.NotificationUseCase
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.TimeUtils
import com.pefdneves.mydots.utils.hasBluetoothPermissions
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val WORKER_PERIOD_MINUTES = 10L
const val BLUETOOTH_CONNECTION_DELAY: Long = 5L
const val PERIODIC_WORKER_ID = "NOTIFICATION_WORKER"

class NotificationScheduler @Inject constructor(
    private val context: Context,
    private val notificationUseCase: NotificationUseCase,
    private val dotsNotificationManager: DotsNotificationManager,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val rxSchedulers: RxSchedulers
) {

    fun runPeriod(isRetry: Boolean = false) {
        if (!hasBluetoothPermissions(context)) {
            dotsNotificationManager.showPermissionsMissingNotification()
        }
        notificationUseCase.getBluetoothDevice()?.let {
            showNotification(it, context, isRetry)
        } ?: kotlin.run {
            cancelNotification()
        }
    }

    fun cancelNotification() {
        dotsNotificationManager.cancelNotification()
    }

    @SuppressLint("CheckResult")
    private fun showNotification(
        bluetoothDevice: BluetoothDevice,
        context: Context?,
        isRetry: Boolean
    ) {
        val deviceName: String =
            sharedPreferencesRepository.getRegisteredModel()?.model ?: bluetoothDevice.name
        val batteryLevel = notificationUseCase.getBatteryLevel(bluetoothDevice)
        if (!notificationUseCase.isDeviceConnected()) {
            if (!isRetry) {
                Completable.timer(BLUETOOTH_CONNECTION_DELAY, TimeUnit.SECONDS, rxSchedulers.main)
                    .subscribe {
                        runPeriod(true)
                    }
                return
            }
        } else {
            cancelNotification()
        }
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
}