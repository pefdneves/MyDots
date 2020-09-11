package com.pefdneves.mydots.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pefdneves.mydots.R
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.utils.ImageUtils
import com.pefdneves.mydots.utils.TimeUtils
import com.vaibhavlakhera.circularprogressview.CircularProgressView

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("deviceConnectedToFillColor")
    fun setDeviceConnectedToFillColor(
        circularProgressView: CircularProgressView,
        isConnected: Boolean
    ) {
        if (isConnected) {
            circularProgressView.setFillColor(circularProgressView.context.getColor(R.color.defaultGreen))
        } else {
            circularProgressView.setFillColor(circularProgressView.context.getColor(R.color.defaultRed))
        }
    }

    @JvmStatic
    @BindingAdapter("batteryLevelToProgressValue")
    fun setBatteryLevelToProgressValue(
        circularProgressView: CircularProgressView,
        batteryInPercentage: Int
    ) {
        circularProgressView.setProgress(batteryInPercentage)
    }

    @JvmStatic
    @BindingAdapter("deviceToImageResource")
    fun setDeviceToImageResource(
        imageView: ImageView,
        selectedDevice: XiaomiSpeakerModel
    ) {
        imageView.setImageResource(ImageUtils.getDrawableIdFromModel(selectedDevice))
    }

    @JvmStatic
    @BindingAdapter("deviceIsConnectedToText")
    fun setDeviceIsConnectedToText(
        textView: TextView,
        isConnected: Boolean
    ) {
        if (isConnected) {
            textView.text = textView.context.getString(R.string.overview_device_is_connected)
        } else {
            textView.text = textView.context.getString(R.string.overview_device_is_not_connected)
        }
    }

    @JvmStatic
    @BindingAdapter("batteryInMinutesToText")
    fun setBatteryInMinutesToText(
        textView: TextView,
        batteryInMinutes: Int
    ) {
        textView.text = TimeUtils.getHoursAndMinutesFromMinutesReadable(batteryInMinutes, textView.context)
    }

    @JvmStatic
    @BindingAdapter("batteryInPercentageToText")
    fun setBatteryInPercentageToText(
        textView: TextView,
        batteryInPercentage: Int
    ) {
        textView.text = if (batteryInPercentage in 0..100)
            textView.context.getString(
                R.string.overview_battery_in_percentage,
                batteryInPercentage
            )
        else
            textView.context.getString(
                R.string.overview_battery_level_unknown
            )
    }

    @JvmStatic
    @BindingAdapter("batteryInPercentageToImageResource")
    fun setBatteryInPercentageToImageResource(
        imageView: ImageView,
        batteryInPercentage: Int
    ) {
        when (batteryInPercentage) {
            100 -> {
                imageView.setImageResource(R.drawable.battery_full)
            }
            in 99 downTo 50 -> {
                imageView.setImageResource(R.drawable.battery_high)
            }
            in 49 downTo 1 -> {
                imageView.setImageResource(R.drawable.battery_low)
            }
            else -> {
                imageView.setImageResource(R.drawable.battery_na)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("deviceIsConnectedToImageResource")
    fun setDeviceIsConnectedToImageResource(
        imageView: ImageView,
        deviceIsConnected: Boolean
    ) {
        if (deviceIsConnected) {
            imageView.setImageResource(R.drawable.toggle_on)
        } else {
            imageView.setImageResource(R.drawable.toggle_off)
        }
    }
}