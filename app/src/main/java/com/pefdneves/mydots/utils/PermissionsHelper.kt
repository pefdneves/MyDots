package com.pefdneves.mydots.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build


fun hasBluetoothPermissions(context: Context): Boolean {
    val permissions =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            listOf(Manifest.permission.BLUETOOTH)
        }
    for (permission in permissions) {
        if (context.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}