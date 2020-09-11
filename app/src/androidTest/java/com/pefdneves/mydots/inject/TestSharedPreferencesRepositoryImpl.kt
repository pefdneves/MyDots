package com.pefdneves.mydots.inject

import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import javax.inject.Inject

class TestSharedPreferencesRepositoryImpl @Inject constructor() : SharedPreferencesRepository {

    private var address = "AA:00"
    private var model = XiaomiSpeakerModel.AIR_DOTS
    private var name = "Mi True Wireless EBs Basic_R"
    private var isNotificationEnabled = false

    override fun isNotificationEnabled(): Boolean {
        return isNotificationEnabled
    }

    override fun setNotificationEnabled(isEnabled: Boolean) {
        isNotificationEnabled = isEnabled
    }

    override fun getRegisteredAddress(): String? {
        return address
    }

    override fun getRegisteredModel(): XiaomiSpeakerModel? {
        return model
    }

    override fun getRegisteredName(): String? {
        return name
    }

    override fun setRegisteredModel(newModel: XiaomiSpeakerModel) {
        model = newModel
    }

    override fun setRegisteredName(newName: String) {
        name = newName
    }

    override fun setRegisteredAddress(newAddress: String) {
        address = newAddress
    }
}