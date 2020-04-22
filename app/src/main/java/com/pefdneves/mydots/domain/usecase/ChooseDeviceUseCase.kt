package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.usecase.base.BaseUseCaseInterface
import com.pefdneves.mydots.model.XiaomiSpeakerModel

interface ChooseDeviceUseCase :
    BaseUseCaseInterface {

    fun saveDevice(name: String, address: String, model: XiaomiSpeakerModel)
}