package com.pefdneves.mydots.inject

import com.pefdneves.mydots.service.MyDotsService
import com.pefdneves.mydots.view.activity.ChooseDeviceActivity
import com.pefdneves.mydots.view.activity.OverviewActivity
import com.pefdneves.mydots.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindOverviewActivity(): OverviewActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindChooseDeviceActivity(): ChooseDeviceActivity

    @ContributesAndroidInjector(modules = [ServiceModule::class])
    abstract fun contributeMyDotsService(): MyDotsService

}