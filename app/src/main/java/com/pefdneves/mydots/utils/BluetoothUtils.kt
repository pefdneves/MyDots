package com.pefdneves.mydots.utils

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel

interface BluetoothUtils {

    fun isDeviceConnectedByBatteryLevel(batteryLevel: Int): Boolean

    fun getDeviceByAddress(devices: Set<BluetoothDevice?>, address: String): BluetoothDevice?

    fun getBatteryLeftBasedOnModel(batteryLevel: Int, xiaomiSpeakerModel: XiaomiSpeakerModel): Int

    fun getBatteryLevelReflection(pairedDevice: BluetoothDevice?): Int
}