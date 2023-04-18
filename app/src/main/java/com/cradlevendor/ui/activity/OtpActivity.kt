package com.cradlevendor.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityOtpBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_otp.*
import org.json.JSONObject

class OtpActivity : BaseActivity(), View.OnClickListener{

    lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityOtpBinding

    private var name : String? = null
    private var encryptedId1 : String? = null
    private var emailMobile : String? = null
    private var mpref: SharaGoPref? = null
    private var otpID : String? = null
    private var resendOtp = true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        initUI()
    }

    private fun initUI() {
        submit_button.setOnClickListener(this)
        tv_SignUp_user.setOnClickListener(this)
        otpID = intent.getStringExtra("otpId")
    }

    private fun statusBarColourChange() {

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

    private fun apiHit(otpJsonObj: JsonObject){
        showLoader()
        mViewModel.otpSignUP(otpJsonObj)
        mViewModel.liveOtpUserRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{
                    dismissLoader()
                }
                is ExceptionHandler.Success->{
                    dismissLoader()
                    Log.d("otp", it.data!!.toString())
                    val jsonObject = JSONObject(it.data!!.toString())
                    val msg = jsonObject.optString("message")
                    showToast(msg)
                    startActivity(Intent(this, VendorLoginActivty::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))

                    /*       val usertype = jsonObject.optString("type")
                           val encryptedId = jsonObject.optString("encryptedId")

                           // Log.d("usertype",user_type);
                           mpref!!.setLoginToken(token)
                           mpref!!.setUserType(usertype)
                           encryptedId1=jsonObject.optString("encryptedId")
                           emailMobile=jsonObject.optString("emailMobile")
                           name=jsonObject.optString("name")
                           Log.e("encryptedId1",encryptedId1.toString())
                           mpref!!.setVideoID(encryptedId1!!)
                           mpref!!.setEmailId(emailMobile!!)
                           mpref!!.setName(name!!)
                           updateUserInfo()
                           startActivity(Intent(this, VendorMainActivity::class.java).putExtra("screen","login"))
                          */ finish()

                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }

    private fun updateUserInfo(){
        val hashMap = HashMap<String,Any>() //define empty hashmap
        hashMap.put("emailMobile",emailMobile!!)
        hashMap.put("name",name!!)
        hashMap.put("fcmKey",mpref!!.getFcmKey("")!!)
        /*  val newUser = MyUser()
          newUser.emailMobile=emailMobile
          newUser.name=name
          newUser.fcmKey=mpref!!.getFcmKey("")*/
        FirebaseDatabase.getInstance().reference.child("users").child(encryptedId1!!).updateChildren(hashMap).addOnCompleteListener(
            OnCompleteListener {

            })
            ?.addOnFailureListener(OnFailureListener { e -> Log.e("error", "" + e) })
        //  FirebaseDatabase.getInstance().reference.child("users").setValue(newUser)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_button -> {
                //  otp=mBinding.editOne.text.toString()+mBinding.editTwo.text.toString()+mBinding.editThree.text.toString()+mBinding.editFour.text.toString()+mBinding.editFive.text.toString()+mBinding.editSix.text.toString()
                if (editOtp.text!!.trim().length == 6) {
                    val otpJsonObj = JsonObject()
                    // otpJsonObj.addProperty("otpId", intent.getStringExtra("otpId"))
                    otpJsonObj.addProperty("otpId", otpID)
                    otpJsonObj.addProperty("otp", editOtp.text!!.trim().toString())
                    otpJsonObj.addProperty("channel", "Android")
                    mViewModel.otpSignUP(otpJsonObj)
                    showLoader()
                    apiHit(otpJsonObj)

                } else {

                    Utility.toastMessage(this@OtpActivity, this.getString(R.string.verify_otp))

                }
            }
            R.id.tv_SignUp_user->{
                if(resendOtp){
                    resendOtpAPi()
                }
            }
        }
    }



}