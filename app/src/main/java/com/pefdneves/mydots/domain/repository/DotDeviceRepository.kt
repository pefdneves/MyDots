package com.pefdneves.mydots.domain.repository

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.DotBluetoothDevice
import io.reactivex.Observable

interface DotDeviceRepository {

    fun getConnectedDevice(): Observable<DotBluetoothDevice>

    fun getPairedDevices(): Observable<Set<BluetoothDevice?>>

}