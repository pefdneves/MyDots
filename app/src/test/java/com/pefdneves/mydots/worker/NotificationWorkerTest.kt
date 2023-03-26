package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.pefdneves.mydots.notification.NotificationScheduler
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class NotificationWorkerTest {

    private lateinit var testSubject: NotificationWorker
    private lateinit var mockNotificationScheduler: NotificationScheduler
    private lateinit var mockContext: Context
    private lateinit var mockWorkerParameters: WorkerParameters

    @Before
    fun setup() {
        mockNotificationScheduler = mockk()
        mockContext = mockk()
        mockWorkerParameters = mockk()
        testSubject =
            NotificationWorker(
                mockContext,
                mockNotificationScheduler,
                mockWorkerParameters
            )
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun test_onStopped() {
        every { mockNotificationScheduler.cancelNotification() } just runs

        testSubject.onStopped()

        verify(exactly = 1) {
            mockNotificationScheduler.cancelNotification()
        }
    }

    @Test
    fun test_do_work(){
        every { mockNotificationScheduler.runPeriod() } just runs

        testSubject.doWork()

        verify(exactly = 1) {
            mockNotificationScheduler.runPeriod()
        }
    }
}