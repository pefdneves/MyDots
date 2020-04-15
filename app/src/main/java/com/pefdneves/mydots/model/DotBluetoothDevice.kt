package com.pefdneves.mydots.model

data class DotBluetoothDevice(
    val name: String?,
    val model: XiaomiSpeakerModel,
    val address: String?,
    val batteryInPercentage: Int,
    val batteryInMinutes: Int,
    val isConnected: Boolean
)