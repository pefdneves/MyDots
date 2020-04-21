package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.usecase.OverviewUseCase
import com.pefdneves.mydots.domain.usecase.OverviewUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideOverviewUseCase(useCase: OverviewUseCaseImpl)
            : OverviewUseCase = useCase

}