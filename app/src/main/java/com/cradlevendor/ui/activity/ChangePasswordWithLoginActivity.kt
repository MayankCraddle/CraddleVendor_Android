package com.cradlevendor.ui.activity

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityChangePassWithLoginBinding
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.MyConstants
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_change_pass_with_login.*
import kotlinx.android.synthetic.main.activity_change_pass_with_login.iv_user_password
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_vendor_login.*
import org.json.JSONObject

class ChangePasswordWithLoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityChangePassWithLoginBinding
    var passwordOld= 0
    var passwordNew= 0
    var passwordConfrim= 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initialiseUI()
    }

    private fun initialiseUI() {
        val response = (application as ApplicationClass).repository
        mBinding = ActivityChangePassWithLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]

        iv_chang_pass.setOnClickListener(this)
        iv_user_password.setOnClickListener(this)
        iv_user_password_new.setOnClickListener(this)
        iv_user_password_confrim.setOnClickListener(this)
        changePassWithApiResult()

    }

    //user OTP result
    private fun changePassWithApiResult(){
        mViewModel.lChangePassWithLogin.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    Log.e(MyConstants.TAG,it.data.toString())
                    val jsonObject = JSONObject(it.data.toString())

                    val message = jsonObject.optString("message")
                    showToast(message)
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
            R.id.iv_chang_pass -> {
                if(old_pass.text.toString().trim().isEmpty())
                    Utility.toastMessage(this,getString(R.string.enter_old_pass))
                else if(new_pass.text.toString().trim().isEmpty())
                    Utility.toastMessage(this,getString(R.string.enter_new_pasword))
                else if (!Utility.isValidPass(new_pass.text.toString().trim().trim { it <= ' ' }))
                    showToast(getString(R.string.pass_validation))
                else if(confrim_pass.text.toString().trim().isEmpty())
                    Utility.toastMessage(this,getString(R.string.enter_confrim_new_pasword))
                else if(confrim_pass.text.toString().trim() != new_pass.text.toString().trim())
                    Utility.toastMessage(this,getString(R.string.enter_match_password))

                else{
                    val listDataChild: JsonObject = JsonObject()
                    listDataChild.addProperty("oldPassword", old_pass.text.toString().trim())
                    listDataChild.addProperty("newPassword", new_pass.text.toString().trim())
                    mViewModel.changePassWithLogin(SharaGoPref.getInstance(this).getLoginToken("")!!,listDataChild)
                    showLoader()
                }
            }
            R.id.iv_back->{
                onBackPressed()
            }
            R.id.iv_user_password->{
                if (passwordOld == 0) {
                    old_pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    iv_user_password.setImageResource(R.drawable.password_show)
                    passwordOld = 1
                } else {
                    old_pass.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    iv_user_password.setImageResource(R.drawable.password_hidden)
                    passwordOld = 0
                }
            }
            R.id.iv_user_password_new->{
                if (passwordNew == 0) {
                    new_pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    iv_user_password_new.setImageResource(R.drawable.password_show)
                    passwordNew = 1
                } else {
                    new_pass.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    iv_user_password_new.setImageResource(R.drawable.password_hidden)
                    passwordNew = 0
                }

            }
            R.id.iv_user_password_confrim->{
                if (passwordConfrim == 0) {
                    confrim_pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    iv_user_password_confrim.setImageResource(R.drawable.password_show)
                    passwordConfrim = 1
                } else {
                    confrim_pass.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    iv_user_password_confrim.setImageResource(R.drawable.password_hidden)
                    passwordConfrim = 0
                }


            }

        }
    }



}
