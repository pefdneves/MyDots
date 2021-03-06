package com.pefdneves.mydots.domain.repository

import com.pefdneves.mydots.model.XiaomiSpeakerModel

interface SharedPreferencesRepository {

    fun isNotificationEnabled(): Boolean

    fun setNotificationEnabled(isEnabled: Boolean)

    fun getRegisteredAddress(): String?

    fun getRegisteredModel(): XiaomiSpeakerModel?

    fun getRegisteredName(): String?

    fun setRegisteredModel(selectedDevice: XiaomiSpeakerModel)

    fun setRegisteredName(name: String)

    fun setRegisteredAddress(name: String)

}