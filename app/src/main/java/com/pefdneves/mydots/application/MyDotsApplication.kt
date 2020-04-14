package com.pefdneves.mydots.application

import com.pefdneves.mydots.inject.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

open class MyDotsApplication : DaggerApplication() {

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
    }

}