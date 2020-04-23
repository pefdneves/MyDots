package com.pefdneves.mydots.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.pefdneves.mydots.R
import com.pefdneves.mydots.model.XiaomiSpeakerModel

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("deviceToImageResource")
    fun setDeviceToImageResource(
        imageView: ImageView,
        selectedDevice: XiaomiSpeakerModel
    ) {
        when (selectedDevice) {
            XiaomiSpeakerModel.AIR_DOTS -> imageView.setImageResource(R.drawable.airdots)
            XiaomiSpeakerModel.AIR_DOTS_PRO_1 -> imageView.setImageResource(R.drawable.airdotspro)
            XiaomiSpeakerModel.AIR_DOTS_PRO_2 -> imageView.setImageResource(R.drawable.airdotspro2)
            XiaomiSpeakerModel.XIAOMI_WIRELESS_BLUETOOTH_SPEAKER -> imageView.setImageResource(R.drawable.wireless_bl_speaker)
            XiaomiSpeakerModel.MI_POCKET_SPEAKER_2 -> imageView.setImageResource(R.drawable.mipocketspeaker2)
            XiaomiSpeakerModel.MI_SPEAKER -> imageView.setImageResource(R.drawable.mispeaker)
            else -> {
                //Do nothing
            }
        }
    }

}