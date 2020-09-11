package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun provideOverviewUseCase(useCase: OverviewUseCaseImpl)
            : OverviewUseCase = useCase

    @Provides
    fun provideChooseDeviceUseCase(useCase: ChooseDeviceUseCaseImpl)
            : ChooseDeviceUseCase = useCase

    @Provides
    @Singleton
    fun provideNotificationUseCase(useCase: NotificationUseCaseImpl)
            : NotificationUseCase = useCase

}