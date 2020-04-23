package com.pefdneves.mydots.viewmodel

import com.pefdneves.mydots.domain.usecase.ChooseDeviceUseCase
import com.pefdneves.mydots.domain.usecase.ChooseDeviceUseCaseImpl
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ChooseDeviceViewModelTest {

    private lateinit var testSubject: ChooseDeviceViewModel
    private lateinit var chooseDeviceUseCase: ChooseDeviceUseCase

    @Before
    fun setUp() {
        chooseDeviceUseCase = mockk<ChooseDeviceUseCaseImpl>()
        testSubject = ChooseDeviceViewModel(chooseDeviceUseCase)
    }

    @Test
    fun test_save() {
        val fakeName = "name"
        val fakeAddress = "address"
        every {
            chooseDeviceUseCase.saveDevice(
                fakeName,
                fakeAddress,
                testSubject.selectedDevice
            )
        } just runs

        testSubject.save(fakeName, fakeAddress)

        verify {
            chooseDeviceUseCase.saveDevice(fakeName, fakeAddress, testSubject.selectedDevice)
        }
    }
}