package com.pefdneves.mydots.inject

import com.pefdneves.mydots.broadcastreceiver.MyDotsBroadcastReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverModule {
    @ContributesAndroidInjector
    abstract fun contributeBroadcastReceiverModule() : MyDotsBroadcastReceiver

}