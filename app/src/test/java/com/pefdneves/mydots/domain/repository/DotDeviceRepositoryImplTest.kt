package com.pefdneves.mydots.domain.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.BluetoothUtilsImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class DotDeviceRepositoryImplTest {

    private lateinit var testSubject: DotDeviceRepositoryImpl
    private lateinit var mockSharedPreferencesRepositoryImpl: SharedPreferencesRepositoryImpl
    private lateinit var mockBluetoothUtilsImpl: BluetoothUtilsImpl

    @Before
    fun setUp() {
        mockSharedPreferencesRepositoryImpl = mockk()
        mockBluetoothUtilsImpl = mockk()
        testSubject =
            DotDeviceRepositoryImpl(mockSharedPreferencesRepositoryImpl, mockBluetoothUtilsImpl)
    }

    @Test
    fun test_getPairedDevices() {
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet

        val result = testSubject.getPairedDevices()

        result.test().assertValue {
            it == fakeSet
        }

        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
        }
    }

    @Test
    fun test_getConnectedDevice() {
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        val fakeAddress = "address"
        val fakeBattery = 50
        val fakeModel = XiaomiSpeakerModel.AIR_DOTS
        val fakeName = "name"
        val fakeBatteryLeftInMinutes = 90
        val fakeConnected = true
        every { mockBluetoothDevice.name } returns fakeName
        every { mockBluetoothDevice.address } returns fakeAddress
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns fakeAddress
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns fakeModel
        every { mockSharedPreferencesRepositoryImpl.getRegisteredName() } returns fakeName
        every {
            mockBluetoothUtilsImpl.getDeviceByAddress(
                fakeSet,
                fakeAddress
            )
        } returns mockBluetoothDevice
        every {
            mockBluetoothUtilsImpl.getBatteryLeftBasedOnModel(
                fakeBattery,
                fakeModel
            )
        } returns fakeBatteryLeftInMinutes
        every { mockBluetoothUtilsImpl.getBatteryLevelReflection(mockBluetoothDevice) } returns fakeBattery
        every { mockBluetoothUtilsImpl.isDeviceConnectedByBatteryLevel(fakeBattery) } returns fakeConnected

        testSubject.getConnectedDevice().test().assertValue {
            it.isConnected == fakeConnected
            it.address == fakeAddress
            it.batteryInMinutes == fakeBatteryLeftInMinutes
            it.batteryInPercentage == fakeBattery
            it.model == fakeModel
            it.name == fakeName
        }

        verify(exactly = 1) {
            mockBluetoothDevice.name
            mockBluetoothDevice.address
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockSharedPreferencesRepositoryImpl.getRegisteredAddress()
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
            mockSharedPreferencesRepositoryImpl.getRegisteredName()
            mockBluetoothUtilsImpl.getBatteryLeftBasedOnModel(
                fakeBattery,
                fakeModel
            )
            mockBluetoothUtilsImpl.getBatteryLevelReflection(mockBluetoothDevice)
            mockBluetoothUtilsImpl.isDeviceConnectedByBatteryLevel(fakeBattery)
        }
    }
}