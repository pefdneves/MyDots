package com.pefdneves.mydots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pefdneves.mydots.view.activity.ChooseDeviceActivity
import com.pefdneves.mydots.inject.TestSharedPreferencesRepositoryImpl
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.testutils.withDrawable
import com.pefdneves.mydots.view.activity.OverviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChooseDeviceActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<ChooseDeviceActivity> =
        ActivityTestRule(ChooseDeviceActivity::class.java)

    @Test
    fun test_image_displayed() {
        onView(withId(R.id.iv_device_choose_device))
            .check(matches(isDisplayed()))

        onView(withId(R.id.iv_device_choose_device))
            .check(matches(withDrawable(R.drawable.airdots)))

        onView(withId(R.id.spn_choose_device))
            .perform(click())

        onView(withText(XiaomiSpeakerModel.MI_SPEAKER.model))
            .perform(click())

        onView(withId(R.id.iv_device_choose_device))
            .check(matches(withDrawable(R.drawable.mispeaker)))
    }

    @Test
    fun test_save() {
        Intents.init()
        onView(withId(R.id.btn_save_choose_device))
            .perform(click())

        onView(withText(TestSharedPreferencesRepositoryImpl().getRegisteredName()))
            .perform(click())

        intended(hasComponent(OverviewActivity::class.java.name))
        Intents.release()
    }
}