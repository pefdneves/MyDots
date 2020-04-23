package com.pefdneves.mydots.application

import com.pefdneves.mydots.inject.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyDotsTestApplication : MyDotsApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerTestAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}