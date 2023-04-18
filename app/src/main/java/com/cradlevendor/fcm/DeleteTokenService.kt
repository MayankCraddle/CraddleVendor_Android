package com.cradlevendor.fcm

import android.app.IntentService
import android.content.Intent
import android.content.SharedPreferences

import java.io.IOException

class DeleteTokenService : IntentService(TAG) {
    private val sharedPreferences: SharedPreferences? = null
    override fun onHandleIntent(intent: Intent?) {
        try {

          //  FirebaseInstanceId.getInstance().deleteInstanceId()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {

        val TAG = DeleteTokenService::class.java.simpleName
    }

}
