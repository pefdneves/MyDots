package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.usecase.base.BaseUseCase
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.RxSchedulers
import javax.inject.Inject

class ChooseDeviceUseCaseImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    schedulers: RxSchedulers
) : ChooseDeviceUseCase,
    BaseUseCase(schedulers) {

    override fun saveDevice(name: String, address: String, model: XiaomiSpeakerModel) {
        sharedPreferencesRepository.setRegisteredModel(model)
        sharedPreferencesRepository.setRegisteredName(name)
        sharedPreferencesRepository.setRegisteredAddress(address)
    }

}