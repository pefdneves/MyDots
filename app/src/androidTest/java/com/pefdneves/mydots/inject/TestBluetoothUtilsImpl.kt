package com.pefdneves.mydots.inject

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.BluetoothUtils
import com.pefdneves.mydots.utils.BluetoothUtilsImpl
import javax.inject.Inject

class TestBluetoothUtilsImpl @Inject constructor(): BluetoothUtils {

    override fun isDeviceConnectedByBatteryLevel(batteryLevel: Int): Boolean {
        return BluetoothUtilsImpl().isDeviceConnectedByBatteryLevel(batteryLevel)
    }

    override fun getDeviceByAddress(
        devices: Set<BluetoothDevice?>,
        address: String
    ): BluetoothDevice? {
        return BluetoothUtilsImpl().getDeviceByAddress(devices, address)
    }

    override fun getBatteryLeftBasedOnModel(
        batteryLevel: Int,
        xiaomiSpeakerModel: XiaomiSpeakerModel
    ): Int {
        return BluetoothUtilsImpl().getBatteryLeftBasedOnModel(batteryLevel, xiaomiSpeakerModel)
    }

    override fun getBatteryLevelReflection(pairedDevice: BluetoothDevice?): Int {
        return 100
    }
}