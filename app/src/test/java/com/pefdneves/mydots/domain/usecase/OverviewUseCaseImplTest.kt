package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.domain.repository.DotDeviceRepositoryImpl
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepositoryImpl
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class OverviewUseCaseImplTest {

    private lateinit var testSubject: OverviewUseCaseImpl
    private lateinit var mockDotDeviceRepositoryImpl: DotDeviceRepositoryImpl
    private lateinit var mockSharedPreferencesRepository: SharedPreferencesRepositoryImpl
    private lateinit var mockNotificationManager: DotsNotificationManagerImpl
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        mockDotDeviceRepositoryImpl = mockk()
        mockSharedPreferencesRepository = mockk()
        mockNotificationManager = mockk()
        testSubject = OverviewUseCaseImpl(
            mockDotDeviceRepositoryImpl,
            mockSharedPreferencesRepository,
            mockNotificationManager,
            RxSchedulers(testScheduler, testScheduler)
        )
    }

    @Test
    fun test_getConnectedDevicesUpdates() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        val mockCallback = mockk<UseCaseCallback<DotBluetoothDevice>>()
        val mockObservable = mockk<Observable<DotBluetoothDevice>>(relaxed = true)
        every { mockDotDeviceRepositoryImpl.getConnectedDevice() } returns mockObservable

        testSubject.getConnectedDevicesUpdates(mockCallback)
        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        verify {
            mockDotDeviceRepositoryImpl.getConnectedDevice()
        }
        RxJavaPlugins.reset()
    }

    @Test
    fun test_isNotificationEnabled() {
        val notificationEnabled = true
        every { mockSharedPreferencesRepository.isNotificationEnabled() } returns notificationEnabled

        val value = testSubject.isNotificationEnabled()

        TestCase.assertEquals(value, notificationEnabled)
        verify(exactly = 1) { mockSharedPreferencesRepository.isNotificationEnabled() }
    }

    @Test
    fun test_setNotificationEnabled() {
        val notificationEnabled = true
        every { mockSharedPreferencesRepository.setNotificationEnabled(notificationEnabled) } just runs
        every { mockNotificationManager.startNotificationService() } just runs

        testSubject.setNotificationEnabled(notificationEnabled)

        verify(exactly = 1) { mockSharedPreferencesRepository.setNotificationEnabled(notificationEnabled) }
    }
}