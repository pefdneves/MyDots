package com.pefdneves.mydots.domain.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.BluetoothUtils
import io.reactivex.Observable
import javax.inject.Inject

class DotDeviceRepositoryImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val bluetoothUtils: BluetoothUtils
) :
    DotDeviceRepository {

    override fun getConnectedDevice(): Observable<DotBluetoothDevice> {
        return Observable.create<DotBluetoothDevice> { emitter ->
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices = bluetoothAdapter.bondedDevices

            sharedPreferencesRepository.getRegisteredAddress()?.let { registeredAddress ->

                val pairedDevice = bluetoothUtils.getDeviceByAddress(
                    pairedDevices, registeredAddress
                )

                val batteryLevel = bluetoothUtils.getBatteryLevelReflection(pairedDevice)

                val registeredModel = sharedPreferencesRepository.getRegisteredModel()
                    ?: XiaomiSpeakerModel.UNKNOWN
                val registeredName = sharedPreferencesRepository.getRegisteredName()

                val device =
                    DotBluetoothDevice(
                        pairedDevice?.name ?: registeredName,
                        registeredModel,
                        pairedDevice?.address ?: registeredAddress,
                        batteryLevel,
                        bluetoothUtils.getBatteryLeftBasedOnModel(
                            batteryLevel,
                            registeredModel
                        ),
                        bluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel)
                    )

                emitter.onNext(device)
            }
        }
    }

    override fun getPairedDevices(): Observable<Set<BluetoothDevice?>> {
        return Observable.create<Set<BluetoothDevice?>> { emitter ->
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices = bluetoothAdapter.bondedDevices
            emitter.onNext(pairedDevices)
        }
    }

}