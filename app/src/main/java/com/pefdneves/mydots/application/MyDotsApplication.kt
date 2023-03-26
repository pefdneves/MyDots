package com.pefdneves.mydots.application

import androidx.work.Configuration
import androidx.work.WorkManager
import com.pefdneves.mydots.inject.DaggerAppComponent
import com.pefdneves.mydots.notification.NotificationScheduler
import com.pefdneves.mydots.worker.NotificationWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

open class MyDotsApplication : DaggerApplication() {

    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
        initializeWorkManager()
    }

    private fun initializeWorkManager() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(NotificationWorkerFactory(notificationScheduler))
            .build()
        WorkManager.initialize(this, config)
    }

}