package com.pefdneves.mydots.domain.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(private val context: Context) :
    SharedPreferencesRepository {

    override fun isNotificationEnabled(): Boolean {
        return context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).getBoolean(
            NOTIFICATION_ENABLED_KEY, true
        )
    }

    override fun setNotificationEnabled(isEnabled: Boolean) {
        context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).edit()
            .putBoolean(NOTIFICATION_ENABLED_KEY, isEnabled).also {
                it.apply()
            }
    }

    override fun getRegisteredModel(): XiaomiSpeakerModel? {
        val registered = context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).getString(
            REGISTERED_MODEL_KEY, null
        )
        return registered?.let {
            XiaomiSpeakerModel.values().firstOrNull { it.name == registered }
        }
    }

    override fun getRegisteredName(): String? {
        return context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).getString(
            REGISTERED_NAME_KEY, null
        )
    }

    override fun getRegisteredAddress(): String? {
        return context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).getString(
            REGISTERED_ADDRESS_KEY, null
        )
    }

    override fun setRegisteredModel(xiaomiSpeakerModel: XiaomiSpeakerModel) {
        context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).edit()
            .putString(REGISTERED_MODEL_KEY, xiaomiSpeakerModel.name).also {
                it.apply()
            }
    }

    override fun setRegisteredName(name: String) {
        context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).edit()
            .putString(REGISTERED_NAME_KEY, name).also {
                it.apply()
            }
    }

    override fun setRegisteredAddress(address: String) {
        context.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE).edit()
            .putString(REGISTERED_ADDRESS_KEY, address).also {
                it.apply()
            }
    }

    companion object {
        private const val NOTIFICATION_ENABLED_KEY = "NOTIFICATION_ENABLED"
        private const val REGISTERED_MODEL_KEY = "REGISTERED_MODEL"
        private const val REGISTERED_ADDRESS_KEY = "REGISTERED_ADDRESS"
        private const val REGISTERED_NAME_KEY = "REGISTERED_NAME"
        private const val SETTINGS_KEY = "SETTINGS"
    }

}