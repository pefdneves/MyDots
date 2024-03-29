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
            XiaomiSpeakerModel.TWS_HONOR_CHOICE -> factor * TWS_HONOR_CHOICE_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_2_SE -> factor * AIR_DOTS_2_SE_BATTERY_MINUTES
            XiaomiSpeakerModel.NICEBOY_HIVE -> factor * NICEBOY_HIVE_BATTERY_MINUTES
            XiaomiSpeakerModel.NICEBOY_HIVE_PODSIE -> factor * NICEBOY_HIVE_PODSIE_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_S -> factor * AIR_DOTS_S_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_3 -> factor * AIR_DOTS_3_BATTERY_MINUTES
            XiaomiSpeakerModel.AIR_DOTS_PRO_3 -> factor * AIR_DOTS_3_PRO_BATTERY_MINUTES
            XiaomiSpeakerModel.FLIPBUDS_PRO -> factor * FLIPBUDS_PRO_BATTERY_MINUTES
            XiaomiSpeakerModel.REDMI_BUDS_3_LITE -> factor * REDMI_BUDS_3_LITE_BATTERY_MINUTES
            XiaomiSpeakerModel.REDMI_BUDS_3_PRO -> factor * REDMI_BUDS_3_PRO_BATTERY_MINUTES
            XiaomiSpeakerModel.XIAOMI_BUDS_3T_PRO -> factor * XIAOMI_BUDS_3T_PRO_BATTERY_MINUTES
            XiaomiSpeakerModel.XIAOMI_BUDS_3 -> factor * XIAOMI_BUDS_3_BATTERY_MINUTES
            XiaomiSpeakerModel.MI_TWS_BASIC_2S -> factor * MI_TWS_BASIC_2_BATTERY_MINUTES
            XiaomiSpeakerModel.MI_TWS_2_PRO -> factor * MI_TWS_PRO_2_BATTERY_MINUTES
            XiaomiSpeakerModel.REDMI_BUDS_ESSENTIAL -> factor * REDMI_BUDS_ESSENTIAL_BATTERY_MINUTES
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
        private const val MI_TWS_PRO_2_BATTERY_MINUTES = 360
        private const val MI_TWS_BASIC_2_BATTERY_MINUTES = 240
        private const val XIAOMI_BUDS_3_BATTERY_MINUTES = 420
        private const val XIAOMI_BUDS_3T_PRO_BATTERY_MINUTES = 360
        private const val REDMI_BUDS_3_PRO_BATTERY_MINUTES = 360
        private const val REDMI_BUDS_3_LITE_BATTERY_MINUTES = 300
        private const val REDMI_BUDS_ESSENTIAL_BATTERY_MINUTES = 330
        private const val FLIPBUDS_PRO_BATTERY_MINUTES = 420
        private const val AIR_DOTS_BATTERY_MINUTES = 240
        private const val AIR_DOTS_S_BATTERY_MINUTES = 240
        private const val AIR_DOTS_3_BATTERY_MINUTES = 360
        private const val AIR_DOTS_3_PRO_BATTERY_MINUTES = 360
        private const val AIR_DOTS_PRO_BATTERY_MINUTES = 180
        private const val AIR_DOTS_PRO_2_BATTERY_MINUTES = 240
        private const val MI_SPEAKER_BATTERY_MINUTES = 480
        private const val MI_POCKET_SPEAKER_2_BATTERY_MINUTES = 420
        private const val XIAOMI_WIRELESS_BLUETOOTH_SPEAKER = 420
        private const val TWS_HONOR_CHOICE_BATTERY_MINUTES = 360
        private const val AIR_DOTS_2_SE_BATTERY_MINUTES = 300
        private const val NICEBOY_HIVE_BATTERY_MINUTES = 780
        private const val NICEBOY_HIVE_PODSIE_BATTERY_MINUTES = 210
    }

}