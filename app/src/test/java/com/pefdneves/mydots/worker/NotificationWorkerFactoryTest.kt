package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.pefdneves.mydots.notification.NotificationScheduler
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class NotificationWorkerFactoryTest {

    private lateinit var mockNotificationScheduler: NotificationScheduler
    private lateinit var testSubject: NotificationWorkerFactory

    @Before
    fun setup() {
        mockNotificationScheduler = mockk()
        testSubject =
            NotificationWorkerFactory(mockNotificationScheduler)
    }

    @Test
    fun test_createWorker() {
        val mockContext = mockk<Context>()
        val fakeString = "String"
        val mockWorkerParameters = mockk<WorkerParameters>()

        val value = testSubject.createWorker(mockContext, fakeString, mockWorkerParameters)

        assert(value is NotificationWorker)
    }

}