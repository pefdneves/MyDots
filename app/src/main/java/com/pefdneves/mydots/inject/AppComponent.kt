package com.pefdneves.mydots.inject

import com.pefdneves.mydots.application.MyDotsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BuildersModule::class]
)
interface AppComponent : AndroidInjector<MyDotsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(myDotsApplication: MyDotsApplication): Builder

        fun build(): AppComponent
    }
}