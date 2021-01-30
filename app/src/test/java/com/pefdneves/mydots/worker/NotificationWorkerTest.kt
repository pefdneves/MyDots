package com.pefdneves.mydots.worker

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.Bitmap
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.pefdneves.mydots.R
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepositoryImpl
import com.pefdneves.mydots.domain.usecase.NotificationUseCaseImpl
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.TimeUtils
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import io.mockk.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NotificationWorkerTest {

    private lateinit var testSubject: NotificationWorker
    private lateinit var mockNotificationManager: DotsNotificationManagerImpl
    private lateinit var mockNotificationUseCaseImpl: NotificationUseCaseImpl
    private lateinit var mockSharedPreferencesRepository: SharedPreferencesRepositoryImpl
    private lateinit var mockContext: Context
    private lateinit var mockWorkerParameters: WorkerParameters

    @Before
    fun setup() {
        mockNotificationManager = mockk()
        mockSharedPreferencesRepository = mockk()
        mockNotificationUseCaseImpl = mockk()
        mockContext = mockk()
        mockWorkerParameters = mockk()
        testSubject =
            NotificationWorker(
                mockContext,
                mockWorkerParameters,
                mockNotificationUseCaseImpl,
                mockNotificationManager,
                mockSharedPreferencesRepository
            )
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun test_onStopped() {
        every { mockNotificationManager.cancelNotification() } just runs

        testSubject.onStopped()

        verify(exactly = 1) {
            mockNotificationManager.cancelNotification()
        }
    }

    @Test
    fun test_do_work(){
        val fakeBatteryLevel = 1
        val fakeBatteryInMinutes = 2
        val fakeHoursAndMinutes = "fake_hours_and_minutes"
        val fakeMessage = "fake_message"
        val fakeImageResource = 12
        val fakeBitmap = mockk<Bitmap>()
        val fakeDeviceModel = XiaomiSpeakerModel.AIR_DOTS
        val mockBluetoothDevice = mockk<BluetoothDevice>()
        val fakeResult = mockk<ListenableWorker.Result>()

        mockkObject(TimeUtils)
        mockkObject(ImageUtils)
        mockkStatic(ListenableWorker.Result::class)


        every { mockNotificationUseCaseImpl.getBluetoothDevice() } returns mockBluetoothDevice
        every { mockNotificationUseCaseImpl.getBatteryLevel(mockBluetoothDevice) } returns fakeBatteryLevel
        every { mockNotificationUseCaseImpl.getBatteryTime(fakeBatteryLevel) } returns fakeBatteryInMinutes
        every { TimeUtils.getHoursAndMinutesFromMinutesReadable(fakeBatteryInMinutes,mockContext) } returns fakeHoursAndMinutes
        every { mockContext.getString(R.string.notification_device_battery, fakeBatteryLevel, fakeHoursAndMinutes) } returns fakeMessage
        every { mockNotificationUseCaseImpl.getImageResourceFromModel() } returns fakeImageResource
        every { ImageUtils.getBitmapFromDrawable(mockContext,fakeImageResource) } returns fakeBitmap
        every { mockNotificationManager.showNotification(fakeDeviceModel.model, fakeMessage, fakeBitmap) } just Runs
        every { ListenableWorker.Result.success() } returns fakeResult
        every { mockSharedPreferencesRepository.getRegisteredModel() } returns fakeDeviceModel

        val result =  testSubject.doWork()
        Assert.assertEquals(fakeResult, result)

        verify(exactly = 1) {
            mockSharedPreferencesRepository.getRegisteredModel()
            mockNotificationUseCaseImpl.getBluetoothDevice()
            mockNotificationUseCaseImpl.getBatteryLevel(mockBluetoothDevice)
            mockNotificationUseCaseImpl.getBatteryTime(fakeBatteryLevel)
            TimeUtils.getHoursAndMinutesFromMinutesReadable(fakeBatteryInMinutes,mockContext)
            mockContext.getString(R.string.notification_device_battery, fakeBatteryLevel, fakeHoursAndMinutes)
            mockNotificationUseCaseImpl.getImageResourceFromModel()
            ImageUtils.getBitmapFromDrawable(mockContext,fakeImageResource)
            mockNotificationManager.showNotification(fakeDeviceModel.model, fakeMessage, fakeBitmap)
            ListenableWorker.Result.success()
        }

    }
}