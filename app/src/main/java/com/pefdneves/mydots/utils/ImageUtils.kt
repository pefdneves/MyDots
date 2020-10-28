package com.pefdneves.mydots.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.pefdneves.mydots.R
import com.pefdneves.mydots.model.XiaomiSpeakerModel

object ImageUtils {

    fun getDrawableIdFromModel(xiaomiSpeakerModel: XiaomiSpeakerModel): Int {
        return when (xiaomiSpeakerModel) {
            XiaomiSpeakerModel.AIR_DOTS -> R.drawable.airdots
            XiaomiSpeakerModel.AIR_DOTS_PRO_1 -> R.drawable.airdotspro
            XiaomiSpeakerModel.AIR_DOTS_PRO_2 -> R.drawable.airdotspro2
            XiaomiSpeakerModel.XIAOMI_WIRELESS_BLUETOOTH_SPEAKER -> R.drawable.wireless_bl_speaker
            XiaomiSpeakerModel.MI_POCKET_SPEAKER_2 -> R.drawable.mipocketspeaker2
            XiaomiSpeakerModel.MI_SPEAKER -> R.drawable.mispeaker
            XiaomiSpeakerModel.TWS_HONOR_CHOICE -> R.drawable.tws_honor_choice
            else -> R.drawable.airdots
        }
    }

    fun getBitmapFromDrawable(context: Context, imageResource: Int): Bitmap? {
        return context.getDrawable(imageResource)?.toBitmap()
    }

}