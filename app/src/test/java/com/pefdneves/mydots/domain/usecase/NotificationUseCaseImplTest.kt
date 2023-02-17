package com.pefdneves.mydots.domain.usecase

import android.app.Notification
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.pefdneves.mydots.R
import com.pefdneves.mydots.domain.repository.DotDeviceRepositoryImpl
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepositoryImpl
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.BluetoothUtils.Companion.DEFAULT_BATTERY_NOT_CONNECT
import com.pefdneves.mydots.utils.BluetoothUtilsImpl
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import io.mockk.*
import io.reactivex.schedulers.TestScheduler
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class NotificationUseCaseImplTest {

    private lateinit var testSubject: NotificationUseCaseImpl
    private lateinit var mockDotDeviceRepositoryImpl: DotDeviceRepositoryImpl
    private lateinit var mockBluetoothUtils: BluetoothUtilsImpl
    private lateinit var mockNotificationManager: DotsNotificationManagerImpl
    private lateinit var mockSharedPreferencesRepositoryImpl: SharedPreferencesRepositoryImpl

    @Before
    fun setup() {
        mockBluetoothUtils = mockk()
        mockDotDeviceRepositoryImpl = mockk()
        mockNotificationManager = mockk()
        mockSharedPreferencesRepositoryImpl = mockk()
        testSubject = NotificationUseCaseImpl(
            mockSharedPreferencesRepositoryImpl, mockBluetoothUtils, mockNotificationManager,
            RxSchedulers(TestScheduler(), TestScheduler())
        )
    }

    @Test
    fun test_isDeviceConnected_connected() {
        val batteryLevel = 100
        val isConnected = true
        val address = "11:22:33:44"
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockBluetoothUtils.getBatteryLevelReflection(mockBluetoothDevice) } returns batteryLevel
        every { mockBluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel) } returns isConnected
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns address
        every { mockBluetoothUtils.getDeviceByAddress(fakeSet, address) } returns mockBluetoothDevice

        val value = testSubject.isDeviceConnected()

        assert(value)
        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockBluetoothUtils.getBatteryLevelReflection(mockBluetoothDevice)
            mockBluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel)
            mockBluetoothUtils.getDeviceByAddress(fakeSet, address)
        }
    }

    @Test
    fun test_isDeviceConnected_disconnected() {
        val batteryLevel = -1
        val isConnected = false
        val address = "11:22:33:44"
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val fakeSet = setOf(null)
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockBluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel) } returns isConnected
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns address
        every { mockBluetoothUtils.getDeviceByAddress(fakeSet, address) } returns null

        val value = testSubject.isDeviceConnected()

        assertFalse(value)
        verify (exactly = 2){
            mockSharedPreferencesRepositoryImpl.getRegisteredAddress()
        }
        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockBluetoothUtils.isDeviceConnectedByBatteryLevel(batteryLevel)
            mockBluetoothUtils.getDeviceByAddress(fakeSet, address)
        }
    }

    @Test
    fun test_getImageResourceFromModel_notNull() {
        val model = XiaomiSpeakerModel.AIR_DOTS_PRO_1
        val resource = R.drawable.airdotspro
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns model

        val value = testSubject.getImageResourceFromModel()

        assertEquals(resource, value)
        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
        }
    }

    @Test
    fun test_getImageResourceFromModel_null() {
        val resource = R.drawable.airdots
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns null

        val value = testSubject.getImageResourceFromModel()

        assertEquals(resource, value)
        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
        }
    }

    @Test
    fun test_getBatteryLevel() {
        val batteryLevel = 100
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        every { mockBluetoothUtils.getBatteryLevelReflection(mockBluetoothDevice) } returns batteryLevel

        val value = testSubject.getBatteryLevel(mockBluetoothDevice)

        assertEquals(batteryLevel, value)
        verify(exactly = 1) {
            mockBluetoothUtils.getBatteryLevelReflection(mockBluetoothDevice)
        }
    }

    @Test
    fun test_getBatteryTime_null() {
        val batteryLevel = 100
        val defaultTime = DEFAULT_BATTERY_NOT_CONNECT
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns null

        val value = testSubject.getBatteryTime(batteryLevel)

        assertEquals(defaultTime, value)
        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
        }
    }

    @Test
    fun test_getBatteryTime_notNull() {
        val batteryLevel = 100
        val batteryTime = 120
        val registeredModel = XiaomiSpeakerModel.AIR_DOTS_PRO_1
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns registeredModel
        every {
            mockBluetoothUtils.getBatteryLeftBasedOnModel(
                batteryLevel,
                registeredModel
            )
        } returns batteryTime

        val value = testSubject.getBatteryTime(batteryLevel)

        assertEquals(batteryTime, value)
        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
            mockBluetoothUtils.getBatteryLeftBasedOnModel(batteryLevel, registeredModel)
        }
    }

    @Test
    fun test_isRegisteredDevice_sameAddress() {
        val registeredModel = XiaomiSpeakerModel.AIR_DOTS_PRO_1
        val address = "11:22:33:44"
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockBluetoothDevice.address } returns address
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns registeredModel
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns address
        every { mockBluetoothUtils.getDeviceByAddress(fakeSet, address) } returns mockBluetoothDevice

        val value = testSubject.isRegisteredDevice()

        assert(value)
        verify(exactly = 3) {
            mockSharedPreferencesRepositoryImpl.getRegisteredAddress()
        }
        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockBluetoothDevice.address
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
        }
    }

    @Test
    fun test_isRegisteredDevice_differentAddress() {
        val registeredModel = XiaomiSpeakerModel.AIR_DOTS_PRO_1
        val address1 = "11:22:33:44"
        val address2 = "11:22:33:45"
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockBluetoothDevice.address } returns address1
        every { mockSharedPreferencesRepositoryImpl.getRegisteredModel() } returns registeredModel
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns address2
        every { mockBluetoothUtils.getDeviceByAddress(fakeSet, address2) } returns mockBluetoothDevice

        val value = testSubject.isRegisteredDevice()

        assertFalse(value)
        verify(exactly = 3) {
            mockSharedPreferencesRepositoryImpl.getRegisteredAddress()
        }
        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockBluetoothDevice.address
            mockSharedPreferencesRepositoryImpl.getRegisteredModel()
        }
    }

    @Test
    fun test_isNotificationEnabled() {
        val enabled = true
        every { mockSharedPreferencesRepositoryImpl.isNotificationEnabled() } returns enabled

        val value = testSubject.isNotificationEnabled()

        assert(value)
        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.isNotificationEnabled()
        }
    }

    @Test
    fun test_getDefaultForegroundNotification() {
        val notification = mockk<Notification>()
        every { mockNotificationManager.getDefaultForegroundNotification() } returns notification

        val value = testSubject.getDefaultForegroundNotification()

        assertSame(notification, value)
        verify(exactly = 1) {
            mockNotificationManager.getDefaultForegroundNotification()
        }
    }

    @Test
    fun test_getDefaultMissingPermissionsNotification() {
        val notification = mockk<Notification>()
        every { mockNotificationManager.getDefaultMissingPermissionsNotification() } returns notification

        val value = testSubject.getDefaultMissingPermissionsNotification()

        assertSame(notification, value)
        verify(exactly = 1) {
            mockNotificationManager.getDefaultMissingPermissionsNotification()
        }
    }

    @Test
    fun test_cancelNotification() {
        every { mockNotificationManager.cancelNotification() } just runs

        testSubject.cancelNotification()

        verify(exactly = 1) {
            mockNotificationManager.cancelNotification()
        }
    }

    @Test
    fun test_getBluetoothDevice() {
        mockkStatic(BluetoothAdapter::class)
        val mockAdapter = mockk<BluetoothAdapter>()
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeSet = setOf(mockBluetoothDevice)
        val address = "11:22:33:44"
        every { BluetoothAdapter.getDefaultAdapter() } returns mockAdapter
        every { mockAdapter.bondedDevices } returns fakeSet
        every { mockSharedPreferencesRepositoryImpl.getRegisteredAddress() } returns address
        every { mockBluetoothUtils.getDeviceByAddress(fakeSet, address) } returns mockBluetoothDevice

        val value = testSubject.getBluetoothDevice()

        assertSame(mockBluetoothDevice, value)

        verify(exactly = 1) {
            BluetoothAdapter.getDefaultAdapter()
            mockAdapter.bondedDevices
            mockBluetoothUtils.getDeviceByAddress(fakeSet, address)
        }
    }
}