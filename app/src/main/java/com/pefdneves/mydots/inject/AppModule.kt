package com.pefdneves.mydots.inject

import android.content.Context
import com.pefdneves.mydots.application.MyDotsApplication
import com.pefdneves.mydots.utils.BluetoothUtils
import com.pefdneves.mydots.utils.BluetoothUtilsImpl
import com.pefdneves.mydots.utils.RxSchedulers
import com.pefdneves.mydots.utils.notification.DotsNotificationManager
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    internal fun provideContext(app: MyDotsApplication) = app.applicationContext

    @Provides
    fun provideRxSchedulers(): RxSchedulers {
        return RxSchedulers(
            network = Schedulers.io(),
            main = AndroidSchedulers.mainThread()
        )
    }

    @Provides
    fun provideBluetoothUtils(): BluetoothUtils {
        return BluetoothUtilsImpl()
    }

    @Provides
    fun provideDotsNotificationManager(context : Context): DotsNotificationManager {
        return DotsNotificationManagerImpl(context)
    }

}