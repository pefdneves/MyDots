package com.pefdneves.mydots

import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class OverviewActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
    }
}
