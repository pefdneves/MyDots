package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.usecase.base.BaseUseCase
import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.utils.RxSchedulers
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OverviewUseCaseImpl @Inject constructor(
    private val dotDeviceRepository: DotDeviceRepository, schedulers: RxSchedulers
) :
    OverviewUseCase, BaseUseCase(schedulers) {

    override fun getConnectedDevicesUpdates(callback: UseCaseCallback<DotBluetoothDevice>) {
        addDisposable(
            Observable.interval(
                OBSERVABLE_INTERVAL_INITIAL_DELAY_SECONDS,
                OBSERVABLE_INTERVAL_PERIOD_SECONDS,
                TimeUnit.SECONDS
            ).flatMap { dotDeviceRepository.getConnectedDevice() }.retry(),
            callback
        )
    }

    companion object {
        private const val OBSERVABLE_INTERVAL_INITIAL_DELAY_SECONDS: Long = 0
        private const val OBSERVABLE_INTERVAL_PERIOD_SECONDS: Long = 2
    }

}