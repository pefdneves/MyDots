package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.domain.repository.DotDeviceRepositoryImpl
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideDotDeviceRepository(repo: DotDeviceRepositoryImpl)
            : DotDeviceRepository = repo

    @Provides
    fun provideSharedPreferencesRepository(repo: SharedPreferencesRepositoryImpl)
            : SharedPreferencesRepository = repo
}