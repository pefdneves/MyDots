package com.pefdneves.mydots.testutils

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

fun getActivityInstance(): Activity? {
    val currentActivity = arrayOf<Activity?>(null)
    getInstrumentation().runOnMainSync(Runnable {
        val resumedActivity =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        val it: Iterator<Activity> = resumedActivity.iterator()
        currentActivity[0] = it.next()
    })
    return currentActivity[0]
}