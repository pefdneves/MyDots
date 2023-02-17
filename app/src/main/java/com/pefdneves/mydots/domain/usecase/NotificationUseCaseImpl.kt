package com.pefdneves.mydots.domain.usecase

import android.app.Notification
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.R
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.usecase.base.BaseUseCase
import com.pefdneves.mydots.utils.BluetoothUtils
import com.pefdneves.mydots.utils.BluetoothUtils.Companion.DEFAULT_BATTERY_NOT_CONNECT
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import javax.inject.Inject

class NotificationUseCaseImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val bluetoothUtils: BluetoothUtils,
    private val notificationManager: DotsNotificationManager,
    schedulers: RxSchedulers
) :
    NotificationUseCase, BaseUseCase(schedulers) {

    override fun isDeviceConnected(): Boolean {
        val batteryLevel = getBluetoothDevice()?.let { device ->
            bluetoothUtils.getBatteryLevelReflection(device)
        } ?: DEFAULT_BATTERY_NOT_CONNECT
        return bluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel)
    }

    override fun getImageResourceFromModel(): Int {
        val registeredModel = sharedPreferencesRepository.getRegisteredModel()
        registeredModel?.let {
            return ImageUtils.getDrawableIdFromModel(it)
        }
        return R.drawable.airdots
    }

    override fun getBatteryLevel(bluetoothDevice: BluetoothDevice): Int {
        return bluetoothUtils.getBatteryLevelReflection(bluetoothDevice)
    }

    override fun getBatteryTime(batteryLevel: Int): Int {
        var batteryTime = DEFAULT_BATTERY_NOT_CONNECT
        sharedPreferencesRepository.getRegisteredModel()?.let {
            batteryTime = bluetoothUtils.getBatteryLeftBasedOnModel(
                batteryLevel,
                it
            )
        }
        return batteryTime
    }

    override fun isRegisteredDevice(): Boolean {
        val registeredModel = sharedPreferencesRepository.getRegisteredModel()
        val registeredAddress = sharedPreferencesRepository.getRegisteredAddress()
        return registeredAddress == getBluetoothDevice()?.address && registeredModel != null
    }

    override fun isNotificationEnabled(): Boolean {
        return sharedPreferencesRepository.isNotificationEnabled()
    }

    override fun getDefaultForegroundNotification(): Notification? {
        return notificationManager.getDefaultForegroundNotification()
    }

    override fun getDefaultMissingPermissionsNotification(): Notification? {
        return notificationManager.getDefaultMissingPermissionsNotification()
    }

    override fun cancelNotification() {
        notificationManager.cancelNotification()
    }

    override fun getBluetoothDevice(): BluetoothDevice? {
        return try {
            val pairedDevices = BluetoothAdapter.getDefaultAdapter()?.bondedDevices
            if (pairedDevices != null && sharedPreferencesRepository.getRegisteredAddress() != null) {
                bluetoothUtils.getDeviceByAddress(
                    pairedDevices,
                    sharedPreferencesRepository.getRegisteredAddress()!!
                )
            } else {
                null
            }
        } catch (exception: SecurityException) {
            null
        }
    }
}