package com.pefdneves.mydots.domain.usecase.base

import com.pefdneves.mydots.utils.RxSchedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

open class BaseUseCase(private val schedulers: RxSchedulers) :
    BaseUseCaseInterface {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun <T> addDisposable(single: Single<T>, callback: UseCaseCallback<T>?) {
        compositeDisposable.add(
            single
                .subscribeOn(schedulers.network)
                .observeOn(schedulers.main)
                .doOnSuccess { callback?.onResult(it) }
                .doOnError { callback?.onError(it) }
                .subscribe()
        )
    }

    fun <T> addDisposable(observable: Observable<T>, callback: UseCaseCallback<T>?) {
        compositeDisposable.add(
            observable
                .subscribeOn(schedulers.network)
                .observeOn(schedulers.main)
                .doOnNext { callback?.onResult(it) }
                .doOnError { callback?.onError(it) }
                .subscribe()
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}