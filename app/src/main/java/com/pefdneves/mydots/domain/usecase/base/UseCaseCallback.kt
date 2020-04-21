package com.pefdneves.mydots.domain.usecase.base

interface UseCaseCallback<T> {

    fun onResult(result: T)

    fun onError(e: Throwable)

}