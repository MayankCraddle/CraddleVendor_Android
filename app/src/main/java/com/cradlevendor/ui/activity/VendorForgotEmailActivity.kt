package com.cradlevendor.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityVendorForgotEmailBinding
import com.cradlevendor.utils.Utility
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_vendor_forgot_email.*
import org.json.JSONObject

class VendorForgotEmailActivity: BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityVendorForgotEmailBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        statusBarColourChange()
    }
    private fun initUI() {
        val response = (application as ApplicationClass).repository
        mBinding = ActivityVendorForgotEmailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel= ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //mBinding.setVariable(BR.onOtpClick,this)

        mBinding.submitButton.setOnClickListener {
            startActivity(Intent(this, VendorVerifyCodeActivty::class.java))
        }
        onClick()
        signUpApiResult()
    }

    private fun onClick() {
        submit_button.setOnClickListener(this)
        mBinding.tvAllreadyAccount.setOnClickListener{finish()}
    }
    //user OTP result
    private fun signUpApiResult(){
        mViewModel.lForgotPassRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    val jsonObject = JSONObject(it.data.toString())
                    val otp_id = jsonObject.optString("otpId")
                    startActivity(Intent(this,ChangePasswordActivity::class.java).putExtra("otp_id",otp_id))
                    finish()
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.submit_button -> {
                if (!Utility.isValidEmail(edit_email.text!!.toString().trim { it <= ' ' })) {
                    Utility.toastMessage(this@VendorForgotEmailActivity,
                        resources.getString(R.string.enter_valid_email))
                }else {

                    val forJsonObj = JsonObject()
                    forJsonObj.addProperty("emailMobile", edit_email.text.toString().trim())

                    mViewModel.vendorForgotPassVeri(forJsonObj)
                    showLoader()

                }

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