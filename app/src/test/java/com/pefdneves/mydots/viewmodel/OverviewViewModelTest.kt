package com.pefdneves.mydots.viewmodel

import com.pefdneves.mydots.domain.usecase.OverviewUseCase
import com.pefdneves.mydots.domain.usecase.OverviewUseCaseImpl
import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class OverviewViewModelTest {

    private lateinit var testSubject: OverviewViewModel
    private lateinit var overviewUseCase: OverviewUseCase

    @Before
    fun setUp() {
        overviewUseCase = mockk<OverviewUseCaseImpl>()
        testSubject = OverviewViewModel(overviewUseCase)
    }

    @Test
    fun test_setup() {
        val isNotificationEnabled = false
        every { overviewUseCase.getConnectedDevicesUpdates(any()) } just runs
        every { overviewUseCase.isNotificationEnabled() } returns isNotificationEnabled
        every { overviewUseCase.setNotificationEnabled(isNotificationEnabled) } just runs

        testSubject.setup()

        verify(exactly = 1) {
            overviewUseCase.getConnectedDevicesUpdates(any())
        }
    }

    @Test
    fun test_callback_not_null() {
        val mockDotBluetoothDevice = mockk<DotBluetoothDevice>()
        val fakeConnected = true
        val fakeBatteryInMinutes = 90
        val fakeBatteryInPercentage = 50
        val fakeModel = XiaomiSpeakerModel.AIR_DOTS
        val fakeName = "name"
        val notificationEnabled = false
        every { overviewUseCase.getConnectedDevicesUpdates(any()) } just runs
        every { overviewUseCase.isNotificationEnabled() } returns notificationEnabled
        every { mockDotBluetoothDevice.isConnected } returns fakeConnected
        every { mockDotBluetoothDevice.batteryInMinutes } returns fakeBatteryInMinutes
        every { mockDotBluetoothDevice.batteryInPercentage } returns fakeBatteryInPercentage
        every { mockDotBluetoothDevice.model } returns fakeModel
        every { mockDotBluetoothDevice.name } returns fakeName

        val capturedCallback = CapturingSlot<UseCaseCallback<DotBluetoothDevice>>()
        testSubject.setup()
        verify { overviewUseCase.getConnectedDevicesUpdates(capture(capturedCallback)) }
        assertTrue(capturedCallback.isCaptured)
        capturedCallback.captured.onResult(mockDotBluetoothDevice)

        verify(exactly = 1) {
            overviewUseCase.isNotificationEnabled()
            mockDotBluetoothDevice.isConnected
            mockDotBluetoothDevice.batteryInMinutes
            mockDotBluetoothDevice.batteryInPercentage
            mockDotBluetoothDevice.model
            mockDotBluetoothDevice.name
        }
        assertEquals(fakeName, testSubject.name)
        assertEquals(fakeBatteryInMinutes, testSubject.batteryInMinutes)
        assertEquals(fakeBatteryInPercentage, testSubject.batteryInPercentage)
        assertEquals(fakeConnected, testSubject.deviceIsConnected)
        assertEquals(fakeModel, testSubject.model)
    }

    @Test
    fun test_callback_null() {
        val mockThrowable = mockk<Throwable>()
        val notificationEnabled = false
        every { overviewUseCase.getConnectedDevicesUpdates(any()) } just runs
        every { overviewUseCase.isNotificationEnabled() } returns notificationEnabled
        every { overviewUseCase.setNotificationEnabled(notificationEnabled) } just runs

        val capturedCallback = CapturingSlot<UseCaseCallback<DotBluetoothDevice>>()
        testSubject.setup()
        verify (exactly = 1){
            overviewUseCase.getConnectedDevicesUpdates(capture(capturedCallback))
            overviewUseCase.isNotificationEnabled()
        }
        assertTrue(capturedCallback.isCaptured)
        capturedCallback.captured.onError(mockThrowable)

        assertEquals("", testSubject.name)
        assertEquals(0, testSubject.batteryInMinutes)
        assertEquals(0, testSubject.batteryInPercentage)
        assertEquals(false, testSubject.deviceIsConnected)
        assertEquals(XiaomiSpeakerModel.UNKNOWN, testSubject.model)
    }

}