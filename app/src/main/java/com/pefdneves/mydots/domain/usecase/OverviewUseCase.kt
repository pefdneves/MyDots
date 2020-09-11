package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.usecase.base.BaseUseCaseInterface
import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.model.DotBluetoothDevice

interface OverviewUseCase :
    BaseUseCaseInterface {

    fun getConnectedDevicesUpdates(callback: UseCaseCallback<DotBluetoothDevice>)

    fun isNotificationEnabled(): Boolean

    fun setNotificationEnabled(isEnabled: Boolean)

}