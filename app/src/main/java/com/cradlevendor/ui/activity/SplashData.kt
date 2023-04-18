package com.cradlevendor.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cradlevendor.chat.model.ChatActivity
import com.cradlevendor.utils.SharaGoPref

class SplashData (context: Context) {
    val healthPref = SharaGoPref.getInstance(context)
    fun getNotificationData(context: Context, extras: Bundle?) {
        if (extras != null) {
            if (extras.getString("icon") == "app_icon") {
                try {
                    pushNotification(extras.getString("title"), extras.getString("body")!!, extras.getString("email")!!, extras.getString("queryID")!!, context)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

    }
     private fun pushNotification(title: String?, message: String, email: String, queryId: String, context: Context) {
        if (healthPref.getUserType("")?.equals("Vendor")!!) {
            val resultIntent = Intent(context, ChatActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("consult", "true")
            resultIntent.putExtra("status", "ACCEPTED")
            resultIntent.putExtra("Key", "active")
            context.startActivity(resultIntent)
            (context as Activity).finish()

        }
    }
}