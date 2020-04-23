package com.pefdneves.mydots.inject

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.model.DotBluetoothDevice
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import javax.inject.Inject

class TestDotDeviceRepositoryImpl @Inject constructor(private val testSharedPreferencesRepositoryImpl: TestSharedPreferencesRepositoryImpl) :
    DotDeviceRepository {

    override fun getConnectedDevice(): Observable<DotBluetoothDevice> {
        return Observable.just(
            DotBluetoothDevice(
                testSharedPreferencesRepositoryImpl.getRegisteredName(),
                testSharedPreferencesRepositoryImpl.getRegisteredModel()!!,
                testSharedPreferencesRepositoryImpl.getRegisteredAddress(),
                100,
                90,
                true
            )
        )
    }

    override fun getPairedDevices(): Observable<Set<BluetoothDevice?>> {
        return Observable.just(
            setOf(
                mockk<BluetoothDevice>().apply {
                    every { address } returns testSharedPreferencesRepositoryImpl.getRegisteredAddress()
                    every { name } returns testSharedPreferencesRepositoryImpl.getRegisteredName()
                }
            )
        )
    }
}