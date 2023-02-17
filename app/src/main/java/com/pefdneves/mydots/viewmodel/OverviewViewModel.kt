package com.pefdneves.mydots.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.pefdneves.mydots.domain.usecase.OverviewUseCase
import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.viewmodel.base.BaseViewModel
import javax.inject.Inject

class OverviewViewModel @Inject constructor(private val overviewUseCase: OverviewUseCase) :
    BaseViewModel(overviewUseCase) {

    @get:Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var model: XiaomiSpeakerModel? = XiaomiSpeakerModel.UNKNOWN
        set(value) {
            field = value
            notifyPropertyChanged(BR.model)
        }

    @get:Bindable
    var batteryInMinutes = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.batteryInMinutes)
        }

    @get:Bindable
    var batteryInPercentage = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.batteryInPercentage)
        }

    @get:Bindable
    var deviceIsConnected = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.deviceIsConnected)
        }

    @Bindable
    var isNotificationEnabled = false
        set(value) {
            if (value != isNotificationEnabled) {
                field = value
                saveNotificationEnabled(value)
                notifyPropertyChanged(BR.isNotificationEnabled)
            }
        }

    private fun saveNotificationEnabled(isEnabled: Boolean) {
        overviewUseCase.setNotificationEnabled(isEnabled)
    }

    override fun setup() {
        overviewUseCase.getConnectedDevicesUpdates(dotDeviceCallback)
        isNotificationEnabled = overviewUseCase.isNotificationEnabled()
    }

    private val dotDeviceCallback = object :
        UseCaseCallback<DotBluetoothDevice> {
        override fun onResult(result: DotBluetoothDevice) {
            applyDevice(result)
        }

        override fun onError(e: Throwable) {
            applyDevice(null)
        }
    }

    private fun applyDevice(result: DotBluetoothDevice?) {
        if (result != null) {
            deviceIsConnected = result.isConnected
            batteryInMinutes = result.batteryInMinutes
            batteryInPercentage = result.batteryInPercentage
            model = XiaomiSpeakerModel.values().firstOrNull { it.model == result.model.model }
            name = result.name ?: ""
        } else {
            deviceIsConnected = false
            batteryInMinutes = 0
            batteryInPercentage = 0
            model = XiaomiSpeakerModel.UNKNOWN
            name = ""
        }
    }

}