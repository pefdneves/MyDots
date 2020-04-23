package com.pefdneves.mydots.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pefdneves.mydots.R
import com.pefdneves.mydots.databinding.ActivityOverviewBinding
import com.pefdneves.mydots.viewmodel.OverviewViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class OverviewActivity : DaggerAppCompatActivity(), LifecycleObserver, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: OverviewViewModel
    private lateinit var dataBinding: ActivityOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OverviewViewModel::class.java)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_overview)
        dataBinding.vm = viewModel
        lifecycle.addObserver(viewModel)
        setListeners()
    }

    private fun setListeners() {
        dataBinding.btnChangeDevice.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_change_device -> onChangeDeviceClicked()
            else -> {
                //do nothing
            }
        }
    }

    private fun onChangeDeviceClicked() {
        startActivity(Intent(this, ChooseDeviceActivity::class.java))
    }
}
