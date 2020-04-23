package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides

@Module
class TestRepositoryModule {

    @Provides
    fun provideDotDeviceRepository(repo: TestDotDeviceRepositoryImpl)
            : DotDeviceRepository = repo

    @Provides
    fun provideSharedPreferencesRepository(repo: TestSharedPreferencesRepositoryImpl)
            : SharedPreferencesRepository = repo
}