package com.pefdneves.mydots.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pefdneves.mydots.viewmodel.ChooseDeviceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(
        ChooseDeviceViewModel::class
    )
    internal abstract fun postChooseDeviceViewModel(
        viewModel: ChooseDeviceViewModel
    ): ViewModel

}