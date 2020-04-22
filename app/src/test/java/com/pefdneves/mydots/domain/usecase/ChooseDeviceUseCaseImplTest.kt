package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.repository.SharedPreferencesRepositoryImpl
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.RxSchedulers
import io.mockk.*
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class ChooseDeviceUseCaseImplTest {

    private lateinit var testSubject: ChooseDeviceUseCaseImpl
    private lateinit var mockSharedPreferencesRepositoryImpl: SharedPreferencesRepositoryImpl
    private val testSchedulers = Schedulers.trampoline()

    @Before
    fun setUp() {
        mockSharedPreferencesRepositoryImpl = mockk()
        testSubject = ChooseDeviceUseCaseImpl(
            mockSharedPreferencesRepositoryImpl,
            RxSchedulers(testSchedulers, testSchedulers)
        )
    }

    @Test
    fun test_saveDevice() {
        val fakeName = "name"
        val fakeAddress = "address"
        val fakeModel = XiaomiSpeakerModel.AIR_DOTS
        every { mockSharedPreferencesRepositoryImpl.setRegisteredAddress(fakeAddress) } just runs
        every { mockSharedPreferencesRepositoryImpl.setRegisteredName(fakeName) } just runs
        every { mockSharedPreferencesRepositoryImpl.setRegisteredModel(fakeModel) } just runs

        testSubject.saveDevice(fakeName, fakeAddress, fakeModel)

        verify(exactly = 1) {
            mockSharedPreferencesRepositoryImpl.setRegisteredAddress(fakeAddress)
            mockSharedPreferencesRepositoryImpl.setRegisteredName(fakeName)
            mockSharedPreferencesRepositoryImpl.setRegisteredModel(fakeModel)
        }
    }
}