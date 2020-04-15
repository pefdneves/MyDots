package com.pefdneves.mydots.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.pefdneves.mydots.domain.BaseUseCaseInterface

open class BaseViewModel(private val useCase: BaseUseCaseInterface?) : ViewModel(), Observable,
    LifecycleObserver {

    private val callbacks = PropertyChangeRegistry()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun setup() {
        //To be overridden
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        this.callbacks.remove(callback);
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        this.callbacks.add(callback);
    }

    fun notifyPropertyChanged(varId: Int) {
        callbacks.notifyCallbacks(this, varId, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onClear() {
        useCase?.clear()
    }

}