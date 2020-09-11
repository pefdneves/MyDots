package com.pefdneves.mydots.domain.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SharedPreferencesRepositoryImplTest {

    private lateinit var testSubject: SharedPreferencesRepository
    private lateinit var mockContext: Context
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        mockContext = mockk()
        mockSharedPreferences = mockk()
        mockSharedPreferencesEditor = mockk()
        every {
            mockContext.getSharedPreferences(
                "SETTINGS",
                MODE_PRIVATE
            )
        } returns mockSharedPreferences
        every {
            mockSharedPreferences.edit()
        } returns mockSharedPreferencesEditor
        testSubject = SharedPreferencesRepositoryImpl(mockContext)
    }

    @Test
    fun test_getRegisteredModel() {
        val fakeModel = "AIR_DOTS"
        every { mockSharedPreferences.getString("REGISTERED_MODEL", null) } returns fakeModel

        val result = testSubject.getRegisteredModel()

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferences.getString("REGISTERED_MODEL", null)
        }
        assertEquals(XiaomiSpeakerModel.AIR_DOTS, result)
    }

    @Test
    fun test_getRegisteredAddress() {
        val fakeAddress = "address"
        every { mockSharedPreferences.getString("REGISTERED_ADDRESS", null) } returns fakeAddress

        val result = testSubject.getRegisteredAddress()

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferences.getString("REGISTERED_ADDRESS", null)
        }
        assertEquals(fakeAddress, result)
    }

    @Test
    fun test_getRegisteredName() {
        val fakeName = "name"
        every { mockSharedPreferences.getString("REGISTERED_NAME", null) } returns fakeName

        val result = testSubject.getRegisteredName()

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferences.getString("REGISTERED_NAME", null)
        }
        assertEquals(fakeName, result)
    }

    @Test
    fun test_setRegisteredName() {
        val fakeName = "name"
        every {
            mockSharedPreferencesEditor.putString(
                "REGISTERED_NAME",
                fakeName
            )
        } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.apply() } just runs

        val result = testSubject.setRegisteredName(fakeName)

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferencesEditor.putString(
                "REGISTERED_NAME",
                fakeName
            )
            mockSharedPreferencesEditor.apply()
        }
    }

    @Test
    fun test_setRegisteredAddress() {
        val fakeAddress = "address"
        every {
            mockSharedPreferencesEditor.putString(
                "REGISTERED_ADDRESS",
                fakeAddress
            )
        } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.apply() } just runs

        val result = testSubject.setRegisteredAddress(fakeAddress)

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferencesEditor.putString(
                "REGISTERED_ADDRESS",
                fakeAddress
            )
            mockSharedPreferencesEditor.apply()
        }
    }

    @Test
    fun test_setRegisteredModel() {
        val fakeModel = XiaomiSpeakerModel.AIR_DOTS
        every {
            mockSharedPreferencesEditor.putString(
                "REGISTERED_MODEL",
                fakeModel.name
            )
        } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.apply() } just runs

        val result = testSubject.setRegisteredModel(fakeModel)

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferencesEditor.putString(
                "REGISTERED_MODEL",
                fakeModel.name
            )
            mockSharedPreferencesEditor.apply()
        }
    }

    @Test
    fun test_isNotificationEnabled() {
        val isEnabled = false
        every { mockSharedPreferences.getBoolean("NOTIFICATION_ENABLED", true) } returns isEnabled

        val result = testSubject.isNotificationEnabled()

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferences.getBoolean("NOTIFICATION_ENABLED", true)
        }
        assertEquals(isEnabled, result)
    }

    @Test
    fun test_setNotificationEnabled() {
        val isEnabled = false
        every {
            mockSharedPreferencesEditor.putBoolean(
                "NOTIFICATION_ENABLED",
                isEnabled
            )
        } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.apply() } just runs

        testSubject.setNotificationEnabled(isEnabled)

        verify(exactly = 1) {
            mockContext.getSharedPreferences("SETTINGS", MODE_PRIVATE)
            mockSharedPreferencesEditor.putBoolean(
                "NOTIFICATION_ENABLED",
                isEnabled
            )
            mockSharedPreferencesEditor.apply()
        }
    }
}