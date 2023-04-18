package com.cradlevendor.base_utils

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cradlevendor.ui.common_fragment.LoaderFragment


abstract class BaseActivity : AppCompatActivity() {



    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    fun back() {
       onBackPressed()
    }

    fun showLoader() {
        try {
            LoaderFragment.showLoader(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissLoader() {
        try {
            LoaderFragment.dismissLoader(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
