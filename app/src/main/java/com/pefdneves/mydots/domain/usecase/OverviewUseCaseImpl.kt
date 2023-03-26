package com.pefdneves.mydots.domain.usecase

import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.usecase.base.BaseUseCase
import com.pefdneves.mydots.domain.usecase.base.UseCaseCallback
import com.pefdneves.mydots.model.DotBluetoothDevice
import com.pefdneves.mydots.notification.NotificationScheduler
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OverviewUseCaseImpl @Inject constructor(
    private val dotDeviceRepository: DotDeviceRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dotsNotificationManager: DotsNotificationManager,
    private val notificationScheduler: NotificationScheduler,
    schedulers: RxSchedulers
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

    override fun isNotificationEnabled(): Boolean {
        return sharedPreferencesRepository.isNotificationEnabled()
    }

    override fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPreferencesRepository.setNotificationEnabled(isEnabled)
        if (!isEnabled) {
            notificationScheduler.cancelNotification()
            dotsNotificationManager.stopNotificationService()
        } else {
            notificationScheduler.runPeriod()
            dotsNotificationManager.startNotificationService()
        }
    }

    companion object {
        private const val OBSERVABLE_INTERVAL_INITIAL_DELAY_SECONDS: Long = 0
        private const val OBSERVABLE_INTERVAL_PERIOD_SECONDS: Long = 2
    }

}