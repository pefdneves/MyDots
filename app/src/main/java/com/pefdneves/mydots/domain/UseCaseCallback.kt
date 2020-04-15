package com.pefdneves.mydots.domain

interface UseCaseCallback<T> {

    fun onResult(result: T)

    fun onError(e: Throwable)

}