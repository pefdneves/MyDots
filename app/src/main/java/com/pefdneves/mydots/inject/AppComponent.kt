package com.pefdneves.mydots.inject

import android.app.Service
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
        AppModule::class,
        BuildersModule::class,
        BroadcastReceiverModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ServiceModule::class,
        UseCaseModule::class]
)
interface AppComponent : AndroidInjector<MyDotsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(myDotsApplication: MyDotsApplication): Builder

        fun build(): AppComponent
    }
}