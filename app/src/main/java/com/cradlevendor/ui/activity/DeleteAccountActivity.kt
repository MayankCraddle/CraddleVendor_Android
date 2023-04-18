package com.cradlevendor.ui.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.BR
import com.cradlevendor.MainActivity
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityDeleteAccountBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.MyHelper
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.logout_dialog.*
import org.json.JSONObject

class DeleteAccountActivity : BaseActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mBinding: ActivityDeleteAccountBinding
    private var token: String? = null
    private var mpref: SharaGoPref? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityDeleteAccountBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
      //  mBinding.setVariable(BR.onSignInClick, this)

        findId()
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    private fun findId() {
        mpref = SharaGoPref.getInstance(this)
        token= SharaGoPref.getInstance(this).getLoginToken("")

     //   mBinding.rlToolBar.setBackgroundColor(SharaGoPref.getInstance(this).getColor(0)!!)


        mBinding.ivBack.setOnClickListener { finish() }


        mBinding.tvConfirmDeActivation.setOnClickListener {
            if(mBinding.namePass.text.toString().trim().isNotEmpty())
                deActivationDialog()
            else
                showToast(getString(R.string.inter_password))
        }
        mBinding.tvEmailId.text=mpref!!.getEmailId("").toString()
    }


    private fun deActivationDialog() {

        val  dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.logout_dialog)
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation1;
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))
        //   dialog!!.tvTitle.text=""
        dialog.rlYes.setOnClickListener {
            deActivateAccountApi()
            dialog.dismiss()
        }

        dialog.rlLogOut.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun deActivateAccountApi(){
        /*   {
               "status":"Deleted/ Deactivated/Active",
               "password":"",
               "comment":""
           }*/
        val deAcJsonObject = JsonObject()
        deAcJsonObject.addProperty("status", "Deleted")
        deAcJsonObject.addProperty("password", mBinding.namePass.text.toString().trim())
        deAcJsonObject.addProperty("comment", "")
        Log.e("json",deAcJsonObject.toString())
        mainViewModel.changeUserAccountStatusParam(mpref!!.getLoginToken("")!!,deAcJsonObject)

        mainViewModel.lchangeUserAccountStatus.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {

                }
                is ExceptionHandler.Success -> {

                    try {
                        //Log.e("TrackOrder>>>", it.data!!.toString())
                        if (it.data != null) {
                            val jsonObject = JSONObject(it.data.toString())
                            Log.e("cancelOrder>>>", jsonObject.toString())
                            val mpref=  SharaGoPref.getInstance(this)
                            mpref.clear()
                            val i = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(i)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                is ExceptionHandler.Error -> {

                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }
    }
}