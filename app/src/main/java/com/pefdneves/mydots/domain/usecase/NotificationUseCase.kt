package com.pefdneves.mydots.domain.usecase

import android.app.Notification
import android.bluetooth.BluetoothDevice

interface NotificationUseCase {

    fun getImageResourceFromModel(): Int

    fun getBatteryLevel(bluetoothDevice: BluetoothDevice): Int

    fun getBatteryTime(batteryLevel: Int): Int

    fun isDeviceConnected(): Boolean

    fun isRegisteredDevice(): Boolean

    fun isNotificationEnabled() : Boolean

    fun getDefaultForegroundNotification(): Notification?

    fun cancelNotification()

    fun getBluetoothDevice(): BluetoothDevice?
}