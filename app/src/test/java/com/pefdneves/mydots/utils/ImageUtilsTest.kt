package com.pefdneves.mydots.utils

import com.pefdneves.mydots.R
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ImageUtilsTest {

    @Test
    fun test_getDrawableIdFromModel() {
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.MI_SPEAKER),
            R.drawable.mispeaker
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.MI_POCKET_SPEAKER_2),
            R.drawable.mipocketspeaker2
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.XIAOMI_WIRELESS_BLUETOOTH_SPEAKER),
            R.drawable.wireless_bl_speaker
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_PRO_2),
            R.drawable.airdotspro2
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_PRO_1),
            R.drawable.airdotspro
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS),
            R.drawable.airdots
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.TWS_HONOR_CHOICE),
            R.drawable.tws_honor_choice
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_2_SE),
            R.drawable.airdots2se
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.NICEBOY_HIVE),
            R.drawable.niceboyhive
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.NICEBOY_HIVE_PODSIE),
            R.drawable.niceboyhive_podsie
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_S),
            R.drawable.airdots
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_3),
            R.drawable.airdots3
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.AIR_DOTS_PRO_3),
            R.drawable.airdotspro3
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.FLIPBUDS_PRO),
            R.drawable.flipbudspro
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.REDMI_BUDS_3_LITE),
            R.drawable.redmibuds3lite
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.REDMI_BUDS_3_PRO),
            R.drawable.redmibuds3pro
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.REDMI_BUDS_ESSENTIAL),
            R.drawable.redmibudsessential
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.XIAOMI_BUDS_3T_PRO),
            R.drawable.xiaomibuds3tpro
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.XIAOMI_BUDS_3),
            R.drawable.xiaomibuds3
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.MI_TWS_BASIC_2S),
            R.drawable.mitwsbasic2s
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.MI_TWS_2_PRO),
            R.drawable.mitws2pro
        )
        assertEquals(
            ImageUtils.getDrawableIdFromModel(XiaomiSpeakerModel.UNKNOWN),
            R.drawable.airdots
        )
    }

}