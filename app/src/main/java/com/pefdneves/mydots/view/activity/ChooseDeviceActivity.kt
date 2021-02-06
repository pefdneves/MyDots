package com.pefdneves.mydots.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.pefdneves.mydots.R
import com.pefdneves.mydots.databinding.ActivityChooseDeviceBinding
import com.pefdneves.mydots.domain.repository.DotDeviceRepository
import com.pefdneves.mydots.model.XiaomiSpeakerModel
import com.pefdneves.mydots.viewmodel.ChooseDeviceViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ChooseDeviceActivity : DaggerAppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var deviceRepository: DotDeviceRepository

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ChooseDeviceViewModel
    private lateinit var dataBinding: ActivityChooseDeviceBinding

    private val listOfDevices =
        XiaomiSpeakerModel.values().filter { it.model != "" }.map { it.model }
    private var selectedDevice: XiaomiSpeakerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChooseDeviceViewModel::class.java)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_device)
        dataBinding.vm = viewModel
        fillSpinner()
        setListeners()
    }

    private fun fillSpinner() {
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, listOfDevices
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBinding.spnChooseDevice.adapter = dataAdapter
        dataBinding.spnChooseDevice.setSelection(0)
        dataBinding.spnChooseDevice.onItemSelectedListener = this
    }

    private fun setListeners() {
        dataBinding.btnSaveChooseDevice.setOnClickListener(this)
        dataBinding.ivDeviceChooseDevice.setOnClickListener {
            dataBinding.spnChooseDevice.performClick()
        }
    }

    private fun onSaveClick() {
        selectedDevice?.let {
            showSelectedBluetoothDeviceDialog()
        }
    }

    private fun showSelectedBluetoothDeviceDialog() {
        deviceRepository.getPairedDevices().doOnNext { bluetoothDeviceSet ->
            if (bluetoothDeviceSet.isNullOrEmpty()) {
                showBluetoothDisabledDialog()
                return@doOnNext
            }
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            alertDialog.setTitle(getString(R.string.choose_your_device_choose_bluetooth_device_title))
            val devices = bluetoothDeviceSet.filter { it?.name != null && it.address != null }
                .mapNotNull { it?.name }.toTypedArray()
            alertDialog.setItems(devices) { dialog, which ->
                bluetoothDeviceSet.firstOrNull {
                    it?.name == devices[which]
                }?.also {
                    createdDeviceAndSave(it.name, it.address)
                    goToOverview()
                }
                dialog.dismiss()
            }
            alertDialog.show()
        }.doOnError {
            showBluetoothDisabledDialog()
        }.subscribe()

    }

    private fun showBluetoothDisabledDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setMessage(getString(R.string.choose_your_device_bluetooth_is_disabled))
        alertDialog.show()
    }

    private fun goToOverview() {
        startActivity(Intent(this, OverviewActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun createdDeviceAndSave(name: String, address: String) {
        viewModel.save(name, address)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save_choose_device -> onSaveClick()
            else -> {
                //do nothing
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        dataBinding.spnChooseDevice.setSelection(0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDevice = XiaomiSpeakerModel.values().firstOrNull {
            it.model == listOfDevices[position]
        }.also {
            it?.let {
                viewModel.selectedDevice = it
            }
        }
    }

}
