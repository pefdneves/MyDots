package com.pefdneves.mydots.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pefdneves.mydots.testutils.getActivityInstance
import com.pefdneves.mydots.testutils.waitFor
import com.pefdneves.mydots.view.activity.OverviewActivity
import com.pefdneves.mydots.view.activity.SplashActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SplashActivity> =
        ActivityTestRule(SplashActivity::class.java)

    @Test
    fun test_go_to_next_screen() {
        onView(isRoot()).perform(waitFor(SplashActivity.DELAY_SPLASH_SCREEN_MILLISECONDS))
        assertTrue(activityRule.activity.isDestroyed)
        assertEquals(
            getActivityInstance()!!.javaClass!!.simpleName,
            OverviewActivity::class.java.simpleName
        )
    }
}