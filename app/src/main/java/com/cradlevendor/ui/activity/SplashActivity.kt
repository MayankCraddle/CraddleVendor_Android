package com.cradlevendor.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.firebase.messaging.FirebaseMessaging
import com.cradlevendor.R
import com.cradlevendor.databinding.ActivitySplashBinding
import com.cradlevendor.utils.SharaGoPref

class SplashActivity : AppCompatActivity(){
    private lateinit var splashScreenBinding: ActivitySplashBinding
    private  var mPref: SharaGoPref?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mPref = SharaGoPref.getInstance(this)

        setHander()
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))

    }

    @SuppressLint("ResourceAsColor")
    private fun setHander() {

        Handler(Looper.getMainLooper()).postDelayed({
            /*  SharaGoPref.getInstance(this).setWhichFrag("Home")
              startActivity(Intent(this@SplashActivity, UserMainActivity::class.java))
              finish()*/
            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                if (result != null)

                    mPref?.setFcmKey(result.toString())
            }

            if(SharaGoPref.getInstance(this@SplashActivity).getLoginToken("")!!.isNotEmpty())
            {

                if(SharaGoPref.getInstance(this).getUserType("")!!.equals(getString(R.string.user))){
                    SharaGoPref.getInstance(this).setWhichFrag("Home")
                    startActivity(Intent(this@SplashActivity, VendorMainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this@SplashActivity, VendorMainActivity::class.java))
                    finish()
                }

            } else{
                startActivity(Intent(this@SplashActivity, VendorLoginActivty::class.java))
                finish()
            }

        },1000)
    }
}