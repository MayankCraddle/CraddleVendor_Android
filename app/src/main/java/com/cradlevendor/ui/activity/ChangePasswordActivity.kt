package com.cradlevendor.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityChangePasswordBinding
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_password.tvTimer
import kotlinx.android.synthetic.main.activity_otp.*
import org.json.JSONObject

class ChangePasswordActivity: BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityChangePasswordBinding
    private var otpID : String? = null
    private var resendOtp = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
      //  mBinding.setVariable(BR.onClickChangePass, this)

        initUI()
    }

    private fun initUI() {
        otpID = intent.getStringExtra("otp_id")
        mBinding.ivChangPass.setOnClickListener{onClickPassChage()}
        changePassApiResult()
        mBinding. tvSignUpUser.setOnClickListener{
            if(resendOtp){
                resendOtpAPi()
            }
        }


    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_SignUp_user->{
              /*  if(resendOtp){
                    resendOtpAPi()
                }*/
            }
        }

    }

    //user Change Pass result
    private fun changePassApiResult(){
        mViewModel.lNewPass.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    Utility.toastMessage(this,getString(R.string.sussfully_change_password))
                    startActivity(Intent(this,VendorLoginActivty::class.java))
                    finish()
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
        mViewModel.lForgotPassRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    Utility.toastMessage(this,getString(R.string.sussfully_change_password))
                    startActivity(Intent(this,VendorLoginActivty::class.java))
                    finish()
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }

    //onClick on Change Password
    private fun onClickPassChage(){
        if(et_otp_id_user.text.toString().trim().isEmpty())
            Utility.toastMessage(this,getString(R.string.enter_otp))
        else if(et_new_password_user.text.toString().trim().isEmpty())
            Utility.toastMessage(this,getString(R.string.enter_new_pasword))
        else if (!Utility.isValidPass(et_new_password_user.text.toString().trim().trim { it <= ' ' }))
            showToast(getString(R.string.pass_validation))
        else if(et_confrim_password_user.text.toString().trim().isEmpty())
            Utility.toastMessage(this,getString(R.string.enter_confrim_new_pasword))
        else if(et_confrim_password_user.text.toString().trim() != et_new_password_user.text.toString().trim())
            Utility.toastMessage(this,getString(R.string.enter_match_password))
        else{
            val changePass = JsonObject()
            with(changePass) {
                addProperty("otpId", otpID)
                addProperty("otp", et_otp_id_user.text.toString().trim())
                addProperty("password", et_new_password_user.text.toString().trim())
            }
            showLoader()

            mViewModel.newPass(changePass)

        }
    }

    private fun resendOtpAPi(){
        val otpJsonObj = JsonObject()
        otpJsonObj.addProperty("otpId", otpID)
        mViewModel.resendOtpReq(otpJsonObj)
        // showLoader()
        timerOtp()
        mViewModel.lResendOtpRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    /* {
                         "otpId" : "11qohOt3qm5YlaTFeutSqCoUXfSqeNyh8","message":"otp resent successfully!"
                     }*/
                    Log.d("response", it.data!!.toString())
                    val jsonObject = JSONObject(it.data.toString())

                    otpID = jsonObject.optString("otpId")
                    val message = jsonObject.optString("message")
                    //   Utility.toastMessage(this,"Otp resent successfully!")


                    /*   startActivity(Intent(this, UserLoginActivity::class.java))
                       finish()*/
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }
    private fun timerOtp(){
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText("Remaining Time: " + millisUntilFinished / 1000)
                resendOtp=false
                // logic to set the EditText could go here
            }

            override fun onFinish() {
                resendOtp=true
                tvTimer.setText("done!")
            }
        }.start()
    }
}
