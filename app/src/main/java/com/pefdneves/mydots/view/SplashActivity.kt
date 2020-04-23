package com.pefdneves.mydots.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.pefdneves.mydots.R
import com.pefdneves.mydots.databinding.ActivitySplashBinding
import com.pefdneves.mydots.domain.repository.SharedPreferencesRepository
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    private lateinit var dataBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        startAnimation()
        goToNextScreen()
    }

    private fun goToNextScreen() {
        Handler().postDelayed({
            val flagsForIntent = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (sharedPreferencesRepository.getRegisteredModel() != null) {
                startActivity(Intent(this, OverviewActivity::class.java).apply {
                    flags = flagsForIntent
                })
            } /*else {
                /TODO: Add screen
                startActivity(Intent(this, ChooseDeviceActivity::class.java).apply {
                    flags = flagsForIntent
                })
            }*/
            overridePendingTransition(0, 0)
        }, DELAY_SPLASH_SCREEN_MILLISECONDS)
    }

    private fun startAnimation() {
        val fadeAnimation = AnimationUtils.loadAnimation(
            this@SplashActivity,
            R.anim.fade_in_out
        )
        dataBinding.ivLogo.startAnimation(fadeAnimation)
    }

    companion object {
        const val DELAY_SPLASH_SCREEN_MILLISECONDS: Long = 2000
    }
}