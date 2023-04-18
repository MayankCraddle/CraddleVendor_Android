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
import com.cradlevendor.databinding.ActivityAddBankAccountBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_add_bank_account.*
import org.json.JSONObject

private lateinit var mViewModel: MainViewModel
private lateinit var mBinding: ActivityAddBankAccountBinding

class AddBankAccountActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityAddBankAccountBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        statusBarColourChange()
        initUI()
    }

    private fun initUI() {
        ivBack.setOnClickListener { finish() }
        rlSubmit.setOnClickListener {
            addBankAccountValidation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    private fun addBankAccountValidation() {
        if (etBankName.text.toString().trim().isEmpty()) {
            showToast("Enter Bank Name")
            return
        }else if (etAccountName.text.toString().trim().isEmpty()) {
            showToast("Enter Account Name")
            return
        } else if (etAccountNumber.text.toString().trim().isEmpty()) {
            showToast("Enter Account Number")
            return
        }/* else if (etBankSortCode.text.toString().trim().isEmpty()) {
            showToast("Enter Bank Sort Code")
            return
        }*/ else {
            var jsonObject = JsonObject()
            var accountJsonObj = JsonObject()
            accountJsonObj.addProperty("accountName", etAccountName.text.toString().trim())
            accountJsonObj.addProperty("bankName", etBankName.text.toString().trim())
            accountJsonObj.addProperty("accountNumber", etAccountNumber.text.toString().trim())
            accountJsonObj.addProperty("bankSortCode", "12")
            jsonObject.add("accountMetadata", accountJsonObj)
            Log.e("jsonobj Account>>", jsonObject.toString())
            apiHit(jsonObject)

        }
    }

    private fun apiHit(jsonObject: JsonObject) {
        showLoader()
        mViewModel.addBankAccountApi(SharaGoPref.getInstance(this).getLoginToken("")!!, jsonObject)
        mViewModel.liveDataAddBankAccountRequest.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {
                    dismissLoader()
                }

                is ExceptionHandler.Success -> {
                    dismissLoader()
                    /*{"message":"User Already Exist!","code":208,"success":false}*/
                    val jsonObject = JSONObject(it.data!!.toString())
                    val success = jsonObject.optString("success")
                    if (success.equals("false")) {
                        showToast(jsonObject.optString("message"))
                    } else {
                        showToast(jsonObject.optString("message"))
                        setResult(RESULT_OK)
                        finish()
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