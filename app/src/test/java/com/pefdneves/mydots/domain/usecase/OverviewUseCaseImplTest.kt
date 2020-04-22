package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.domain.repository.DotDeviceRepositoryImpl
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.utils.RxSchedulers
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class OverviewUseCaseImplTest {

    private lateinit var testSubject: OverviewUseCaseImpl
    private lateinit var mockDotDeviceRepositoryImpl: DotDeviceRepositoryImpl
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        mockDotDeviceRepositoryImpl = mockk()
        testSubject = OverviewUseCaseImpl(
            mockDotDeviceRepositoryImpl,
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
}