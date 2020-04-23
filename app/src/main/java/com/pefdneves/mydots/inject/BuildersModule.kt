package com.pefdneves.mydots.inject

import com.pefdneves.mydots.view.OverviewActivity
import com.pefdneves.mydots.view.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindOverviewActivity(): OverviewActivity

}