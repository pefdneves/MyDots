package com.pefdneves.mydots.inject

import com.pefdneves.mydots.OverviewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun bindOverviewActivity(): OverviewActivity

}