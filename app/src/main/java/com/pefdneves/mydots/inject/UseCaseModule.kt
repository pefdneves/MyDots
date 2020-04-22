package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.usecase.ChooseDeviceUseCase
import com.pefdneves.mydots.domain.usecase.ChooseDeviceUseCaseImpl
import com.pefdneves.mydots.domain.usecase.OverviewUseCase
import com.pefdneves.mydots.domain.usecase.OverviewUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideOverviewUseCase(useCase: OverviewUseCaseImpl)
            : OverviewUseCase = useCase

    @Provides
    fun provideChooseDeviceUseCase(useCase: ChooseDeviceUseCaseImpl)
            : ChooseDeviceUseCase = useCase

}