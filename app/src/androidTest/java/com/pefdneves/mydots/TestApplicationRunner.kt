package com.pefdneves.mydots

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.pefdneves.mydots.application.MyDotsTestApplication

class TestApplicationRunner : AndroidJUnitRunner() {

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        val testApplicationClassName = MyDotsTestApplication::class.java.canonicalName
        return super.newApplication(cl, testApplicationClassName, context)
    }
}