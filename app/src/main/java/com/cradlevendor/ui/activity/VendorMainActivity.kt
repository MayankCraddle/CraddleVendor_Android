package com.cradlevendor.ui.activity

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityVendorMainBinding
import com.cradlevendor.ui.fragment.CancelOrderFragment
import com.cradlevendor.ui.fragment.VendorProfileFragment
import com.cradlevendor.ui.fragment.ReceivedOrderFragment
import com.cradlevendor.vendor.ui.fragment.VendorDeliveredFragment
import com.cradlevendor.vendor.ui.fragment.VendorProcessingFragment
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory

import kotlinx.android.synthetic.main.activity_vendor_main.*
import kotlinx.android.synthetic.main.fragment_vendor_receivedorder.*

class VendorMainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityVendorMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        statusBarColourChange()
    }

    private fun initUI() {
        val response = (application as ApplicationClass).repository
        mBinding = ActivityVendorMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        // mBinding.setVariable(BR.onOtpClick,this)

        onClick()
    }

    private fun onClick() {
        openReceviedFrag()
        addFragment(ReceivedOrderFragment())
        ll_profile.setOnClickListener(this)
        llCancelOder.setOnClickListener(this)
        ll_delivered.setOnClickListener(this)
        ll_received_order.setOnClickListener(this)
        ll_processing.setOnClickListener(this)

    }

    private fun openReceviedFrag() {
        iv_received_order.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP)
        iv_processing.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_delivered.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_profile.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )

        tv_received_vendor.setTextColor(ContextCompat.getColor(this, R.color.black))
        tv_process_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_delivered_order_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_profile_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))

        iv_cancel_order.setColorFilter(ContextCompat.getColor(this, R.color.home_text), PorterDuff.Mode.SRC_ATOP)
        tv_cancel_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
    }
    private fun openCancelFrag() {
        iv_cancel_order.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP)
        iv_processing.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_received_order.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_delivered.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_profile.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )

        tv_received_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_process_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_delivered_order_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_profile_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_cancel_vendor.setTextColor(ContextCompat.getColor(this, R.color.black))

    }


    fun addFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    private fun openProcessingFrag() {
        iv_received_order.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_processing.setColorFilter(
            ContextCompat.getColor(this, R.color.black),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_delivered.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_profile.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )

        tv_received_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_process_vendor.setTextColor(ContextCompat.getColor(this, R.color.black))
        tv_delivered_order_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_profile_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))

        iv_cancel_order.setColorFilter(ContextCompat.getColor(this, R.color.home_text), PorterDuff.Mode.SRC_ATOP)
        tv_cancel_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
    }

    private fun openDeliveredFrag() {
        iv_received_order.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_processing.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_delivered.setColorFilter(
            ContextCompat.getColor(this, R.color.black),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_profile.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )

        tv_received_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_process_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_delivered_order_vendor.setTextColor(ContextCompat.getColor(this, R.color.black))
        tv_profile_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        iv_cancel_order.setColorFilter(ContextCompat.getColor(this, R.color.home_text), PorterDuff.Mode.SRC_ATOP)
        tv_cancel_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
    }

    private fun openProfileFrag() {
        iv_received_order.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_processing.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_delivered.setColorFilter(
            ContextCompat.getColor(this, R.color.home_text),
            PorterDuff.Mode.SRC_ATOP
        )
        iv_profile.setColorFilter(
            ContextCompat.getColor(this, R.color.black),
            PorterDuff.Mode.SRC_ATOP
        )

        tv_received_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_process_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_delivered_order_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
        tv_profile_vendor.setTextColor(ContextCompat.getColor(this, R.color.black))

        iv_cancel_order.setColorFilter(ContextCompat.getColor(this, R.color.home_text), PorterDuff.Mode.SRC_ATOP)
        tv_cancel_vendor.setTextColor(ContextCompat.getColor(this, R.color.home_text))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_received_order -> {
           openReceviedFrag()
                addFragment(ReceivedOrderFragment())
            } R.id.llCancelOder -> {
           openCancelFrag()
                addFragment(CancelOrderFragment())
            }
            R.id.ll_processing -> {
                openProcessingFrag()
                addFragment(VendorProcessingFragment())

            }
            R.id.ll_delivered -> {
                openDeliveredFrag()
                addFragment(VendorDeliveredFragment())
            }
            R.id.ll_profile -> {
                openProfileFrag()
                addFragment(VendorProfileFragment())

            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }
}