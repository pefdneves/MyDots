package com.pefdneves.mydots.utils

import io.reactivex.Scheduler

open class RxSchedulers(val network: Scheduler, val main: Scheduler)
