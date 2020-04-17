package com.pefdneves.mydots.utils

import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class BluetoothUtilsImplTest {

    private lateinit var testSubject: BluetoothUtilsImpl

    @Before
    fun setUp() {
        testSubject = BluetoothUtilsImpl()
    }

    @Test
    fun test_isDeviceConnectedByBatteryLevel_invalid() {
        val value = -1

        val result = testSubject.isDeviceConnectedByBatteryLevel(value)

        assertFalse(result)
    }

    @Test
    fun test_isDeviceConnectedByBatteryLevel_valid() {
        val value = 50

        val result = testSubject.isDeviceConnectedByBatteryLevel(value)

        assertTrue(result)
    }

    @Test
    fun test_getDeviceByAddress_exists() {
        val mockBluetoothDevice1 = mockk<BluetoothDevice>()
        val mockBluetoothDevice2 = mockk<BluetoothDevice>()
        val fakeAddress1 = "1"
        val fakeAddress2 = "2"
        val fakeSet = setOf(mockBluetoothDevice1, mockBluetoothDevice2)
        every { mockBluetoothDevice1.address } returns fakeAddress1
        every { mockBluetoothDevice2.address } returns fakeAddress2

        val result = testSubject.getDeviceByAddress(fakeSet, fakeAddress2)

        verify(exactly = 1) {
            mockBluetoothDevice1.address
            mockBluetoothDevice2.address
        }
        assertSame(result, mockBluetoothDevice2)
    }

    @Test
    fun test_getDeviceByAddress_not_exists() {
        val mockBluetoothDevice1 = mockk<BluetoothDevice>()
        val mockBluetoothDevice2 = mockk<BluetoothDevice>()
        val fakeAddress1 = "1"
        val fakeAddress2 = "2"
        val fakeAddress3 = "3"
        val fakeSet = setOf(mockBluetoothDevice1, mockBluetoothDevice2)
        every { mockBluetoothDevice1.address } returns fakeAddress1
        every { mockBluetoothDevice2.address } returns fakeAddress2

        val result = testSubject.getDeviceByAddress(fakeSet, fakeAddress3)

        verify(exactly = 1) {
            mockBluetoothDevice1.address
            mockBluetoothDevice2.address
        }
        assertNull(result)
    }

    @Test
    fun test_getBatteryLeftBasedOnModel_airdots() {
        val fakeBattery = 100
        val fakeXiaomiSpeakerModel = XiaomiSpeakerModel.AIR_DOTS

        val result = testSubject.getBatteryLeftBasedOnModel(fakeBattery, fakeXiaomiSpeakerModel)

        assertEquals(result, 240)
    }

    @Test
    fun test_getBatteryLeftBasedOnModel_invalidbattery() {
        val fakeBattery = -1
        val fakeXiaomiSpeakerModel = XiaomiSpeakerModel.AIR_DOTS

        val result = testSubject.getBatteryLeftBasedOnModel(fakeBattery, fakeXiaomiSpeakerModel)

        assertEquals(result, 0)
    }

    @Test
    fun test_getBatteryLeftBasedOnModel_invalidmodel() {
        val fakeBattery = 100
        val fakeXiaomiSpeakerModel = XiaomiSpeakerModel.UNKNOWN

        val result = testSubject.getBatteryLeftBasedOnModel(fakeBattery, fakeXiaomiSpeakerModel)

        assertEquals(result, 0)
    }
}