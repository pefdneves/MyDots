package com.pefdneves.mydots.utils.notification

import android.app.Notification
import android.graphics.Bitmap

interface DotsNotificationManager {

    fun getDefaultForegroundNotification() : Notification

    fun showNotification(title: String, message: String, largeIcon: Bitmap)

    fun cancelNotification()

    fun startNotificationService()

    fun stopNotificationService()

    fun showPermissionsMissingNotification()

    fun getDefaultMissingPermissionsNotification(): Notification?
}