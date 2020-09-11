package com.pefdneves.mydots.inject

import com.pefdneves.mydots.application.MyDotsTestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
        BuildersModule::class,
        BroadcastReceiverModule::class,
        UseCaseModule::class,
        TestRepositoryModule::class,
        ServiceModule::class,
        ViewModelModule::class]
)
interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(myDotsApplication: MyDotsTestApplication): Builder

        fun build(): TestAppComponent
    }

    fun inject(myDotsApplication: MyDotsTestApplication)
}