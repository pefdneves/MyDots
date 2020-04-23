package com.pefdneves.mydots.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pefdneves.mydots.R
import com.pefdneves.mydots.inject.TestDotDeviceRepositoryImpl
import com.pefdneves.mydots.inject.TestSharedPreferencesRepositoryImpl
import com.pefdneves.mydots.testutils.withDrawable
import com.pefdneves.mydots.utils.TimeUtils
import com.pefdneves.mydots.view.activity.OverviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<OverviewActivity> =
        ActivityTestRule(OverviewActivity::class.java)

    @Test
    fun test_isConnected_toggle() {
        onView(withId(R.id.iv_connected_toggle))
            .check(matches(withDrawable(R.drawable.toggle_on)))
    }

    @Test
    fun test_battery_icon() {
        onView(withId(R.id.iv_battery_icon))
            .check(matches(withDrawable(R.drawable.battery_full)))
    }

    @Test
    fun test_device_name() {
        onView(withId(R.id.tv_device_name))
            .check(matches(withText(TestSharedPreferencesRepositoryImpl().getRegisteredName())))
    }

    @Test
    fun test_device_model() {
        onView(withId(R.id.tv_title_overview))
            .check(matches(withText(TestSharedPreferencesRepositoryImpl().getRegisteredModel()!!.model)))
    }

    @Test
    fun test_battery_percentage() {
        val batteryText = activityRule.activity.applicationContext.getString(
            R.string.overview_battery_in_percentage,
            TestDotDeviceRepositoryImpl(TestSharedPreferencesRepositoryImpl()).getConnectedDevice().blockingFirst().batteryInPercentage
        )
        onView(withId(R.id.tv_battery_percentage))
            .check(matches(withText(batteryText)))
    }

    @Test
    fun test_battery_minutes() {
        val pair = TimeUtils.getHoursAndMinutesFromMinutes(
            TestDotDeviceRepositoryImpl(TestSharedPreferencesRepositoryImpl()).getConnectedDevice().blockingFirst().batteryInMinutes
        )
        val batteryText = activityRule.activity.applicationContext.getString(
            R.string.overview_battery_in_time_hours_and_minutes,
            pair.first,
            pair.second
        )
        onView(withId(R.id.tv_battery_minutes))
            .check(matches(withText(batteryText)))
    }

    @Test
    fun test_device_picture() {
        onView(withId(R.id.iv_device_overview))
            .check(matches(withDrawable(R.drawable.airdots)))
    }
}