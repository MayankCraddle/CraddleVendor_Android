package com.cradlevendor.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivitySignupAccountDetailBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup_account_detail.*
import org.json.JSONObject

class SignupAccountDetailActivity : BaseActivity() {
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivitySignupAccountDetailBinding
    private var jsonobj = JsonObject()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivitySignupAccountDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        initUI()
    }

    private fun initUI() {
        rlSubmit.setOnClickListener {
            validationAccountDetail()
        }
        ivBack.setOnClickListener { finish() }
        try {
            var jsonStr = intent.getStringExtra("json")
            jsonobj = JsonParser().parse(jsonStr).asJsonObject;

        } catch (e: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    private fun validationAccountDetail() {
         if (etBankName.text.toString().trim().isEmpty()) {
            showToast("Enter Bank Name")
            return
        }else if (etAccountName.text.toString().trim().isEmpty()) {
            showToast("Enter Account Name")
            return
        } else if (etAccountNumber.text.toString().trim().isEmpty()) {
            showToast("Enter Account Number")
            return
        } /* else if (etBankSortCode.text.toString().trim().isEmpty()) {
            showToast("Enter Bank Sort Code")
            return
        } */else {
            var accountJsonObj = JsonObject()
            accountJsonObj.addProperty("accountName", etAccountName.text.toString().trim())
            accountJsonObj.addProperty("bankName", etBankName.text.toString().trim())
            accountJsonObj.addProperty("accountNumber", etAccountNumber.text.toString().trim())
            accountJsonObj.addProperty("bankSortCode", "12")
            jsonobj.add("accountMetadata", accountJsonObj)
            Log.e("jsonobj Account>>", jsonobj.toString())
            apiHit(jsonobj)

        }
    }

    private fun apiHit(jsonObject: JsonObject) {
        showLoader()
        mViewModel.userSignup(jsonObject)
        mViewModel.mLiveDataUserSignUpRequest.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {
                    dismissLoader()
                }

                is ExceptionHandler.Success -> {
                    dismissLoader()
                    /*{"message":"User Already Exist!","code":208,"success":false}*/
                    val jsonObject = JSONObject(it.data!!.toString())
                    Log.e("SignUP", jsonObject.toString())
                    val success = jsonObject.optBoolean("success")
                    val message = jsonObject.optString("message")
                    if (success) {
                        startActivity(
                            Intent(this, OtpActivity::class.java).putExtra(
                                "otpId", jsonObject.optString("otpId")
                            )
                        )
                        finish()
                      //  showToast("User Already Exist")
                    } else {
                        showToast(message)


                    }

                }
                is ExceptionHandler.Error -> {
                    dismissLoader()
                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }
    }

}