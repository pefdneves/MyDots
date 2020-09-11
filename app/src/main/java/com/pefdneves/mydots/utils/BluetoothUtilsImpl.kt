package com.pefdneves.mydots.utils

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import javax.inject.Inject
import kotlin.math.roundToInt

class BluetoothUtilsImpl @Inject constructor() : BluetoothUtils {

    override fun isDeviceConnectedByBatteryLevel(batteryLevel: Int): Boolean {
        return batteryLevel in 0..100
    }

    override fun getDeviceByAddress(
        devices: Set<BluetoothDevice?>,
        address: String
    ): BluetoothDevice? {
        for (bluetoothDevice in devices) {
            bluetoothDevice?.let {
                if (it.address == address)
                    return bluetoothDevice
            }
        }
        return null
    }

    override fun getBatteryLeftBasedOnModel(
        batteryLevel: Int,
        xiaomiSpeakerModel: XiaomiSpeakerModel
    ): Int {
        val factor: Double = batteryLevel / 100.0
        val minutes = when (xiaomiSpeakerModel) {
            XiaomiSpeakerModel.AIR_DOTS -> factor * AIR_DOTS_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_PRO_1 -> factor * AIR_DOTS_PRO_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_PRO_2 -> factor * AIR_DOTS_PRO_2_BATTERY_MINUTES
            XiaomiSpeakerModel.MI_SPEAKER -> factor * MI_SPEAKER_BATTERY_MINUTES
            XiaomiSpeakerModel.MI_POCKET_SPEAKER_2 -> factor * MI_POCKET_SPEAKER_2_BATTERY_MINUTES
            XiaomiSpeakerModel.XIAOMI_WIRELESS_BLUETOOTH_SPEAKER -> factor * XIAOMI_WIRELESS_BLUETOOTH_SPEAKER
            else -> {
                -1.0
            }
        }
        return if (minutes > 0)
            minutes.roundToInt()
        else
            0
    }

    override fun getBatteryLevelReflection(pairedDevice: BluetoothDevice?): Int {
        return pairedDevice?.let { bluetoothDevice ->
            (bluetoothDevice.javaClass.getMethod("getBatteryLevel")).invoke(pairedDevice) as Int
        } ?: BluetoothUtils.DEFAULT_BATTERY_NOT_CONNECT
    }

    companion object {
        private const val AIR_DOTS_BATTERY_MINUTES = 240
        private const val AIR_DOTS_PRO_BATTERY_MINUTES = 180
        private const val AIR_DOTS_PRO_2_BATTERY_MINUTES = 240
        private const val MI_SPEAKER_BATTERY_MINUTES = 480
        private const val MI_POCKET_SPEAKER_2_BATTERY_MINUTES = 420
        private const val XIAOMI_WIRELESS_BLUETOOTH_SPEAKER = 420
    }

}