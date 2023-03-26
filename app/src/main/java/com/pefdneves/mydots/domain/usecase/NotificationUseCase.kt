package com.pefdneves.mydots.domain.usecase

import android.bluetooth.BluetoothDevice

interface NotificationUseCase {

    fun getImageResourceFromModel(): Int

    fun getBatteryLevel(bluetoothDevice: BluetoothDevice): Int

    fun getBatteryTime(batteryLevel: Int): Int

    fun isDeviceConnected(): Boolean

    fun isRegisteredDevice(): Boolean

    fun isNotificationEnabled() : Boolean

    fun getBluetoothDevice(): BluetoothDevice?
}