package com.pefdneves.mydots.utils

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.pefdneves.mydots.R
import com.pefdneves.mydots.utils.notification.DotsNotificationManagerImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class DotsNotificationManagerImplTest {

    private lateinit var testSubject: DotsNotificationManagerImpl
    private lateinit var device: UiDevice

    @Before
    fun setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        testSubject =
            DotsNotificationManagerImpl(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun test_show_notification(){
        val testTitle = "Title"
        val testText = "Message"
        val imageResource = R.drawable.airdots
        val bitmap = ImageUtils.getBitmapFromDrawable(
            InstrumentationRegistry.getInstrumentation().targetContext,
            imageResource
        )

        testSubject.showNotification(testTitle, testText, bitmap!!)

        device.openNotification()
        device.wait(Until.hasObject(By.text(testText)), 5000)

        Assert.assertEquals(testTitle, device.findObject(By.text(testTitle)).text)
        Assert.assertEquals(testText, device.findObject(By.text(testText)).text)
    }

    @After
    fun tearDown(){
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        InstrumentationRegistry.getInstrumentation().targetContext.sendBroadcast(it)
    }

}