package com.pefdneves.mydots.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.pefdneves.mydots.domain.usecase.NotificationUseCaseImpl
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class NotificationWorkerFactoryTest {

    private lateinit var mockNotificationManager: DotsNotificationManagerImpl
    private lateinit var mockNotificationUseCaseImpl: NotificationUseCaseImpl
    private lateinit var testSubject: NotificationWorkerFactory

    @Before
    fun setup() {
        mockNotificationManager = mockk()
        mockNotificationUseCaseImpl = mockk()
        testSubject =
            NotificationWorkerFactory(mockNotificationUseCaseImpl, mockNotificationManager)
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