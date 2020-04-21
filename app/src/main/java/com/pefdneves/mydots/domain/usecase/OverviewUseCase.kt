package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.BaseUseCaseInterface
import com.pefdneves.mydots.domain.UseCaseCallback
import com.pefdneves.mydots.model.DotBluetoothDevice

interface OverviewUseCase :
    BaseUseCaseInterface {

    fun getConnectedDevicesUpdates(callback: UseCaseCallback<DotBluetoothDevice>)

}