package com.pefdneves.mydots.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.pefdneves.mydots.domain.usecase.ChooseDeviceUseCase
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.viewmodel.base.BaseViewModel
import javax.inject.Inject

class ChooseDeviceViewModel @Inject constructor(private val chooseDeviceUseCase: ChooseDeviceUseCase) :
    BaseViewModel(null) {

    @get:Bindable
    var selectedDevice = XiaomiSpeakerModel.AIR_DOTS
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedDevice)
        }

    fun save(name: String, address: String) {
        chooseDeviceUseCase.saveDevice(name, address, selectedDevice)
    }

}