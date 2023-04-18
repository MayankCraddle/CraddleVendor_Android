package com.cradlevendor.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cradlevendor.R
import com.cradlevendor.chat.model.conversations

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun Context.showToast(message: CharSequence?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun log(mess:String?){
    Log.e("HIVe",mess.toString())
}


/*
fun getActionRequired(context: Context, actionRequired: String?,message: String?,email: String?,loginAs:String?,mpinId:String?,otpId:String){
    if (actionRequired == "SET_MPIN") {
        FunctionHelper.showToastLong(context, message!!)
        (context as Activity).startActivity(
            Intent(context, CreateMpinActivity::class.java)
            .putExtra("set_pin", "1")
            .putExtra("login_email", email)
            .putExtra("login_as", loginAs))
    } else if (actionRequired == "VERIFY_EMAIL_OTP") {
        FunctionHelper.showToastLong(context, message!!)
        (context as Activity).startActivity(
            Intent(context, OtpVerificationActivity::class.java)
            .putExtra("set_pin", "1")
            .putExtra("login_email", email)
            .putExtra("login_as", loginAs)
            .putExtra("otpId", otpId))
    } else {
        FunctionHelper.showToastLong(context, message!!)
        (context as Activity).startActivity(
            Intent(context, EnterMpinActivity::class.java)
            .putExtra("mpin_id", mpinId)
            .putExtra("login_email", email)
            .putExtra("login_as", loginAs))
    }
}
*/
fun getTimeFormat(data: conversations):String{
    val timeFormat = SimpleDateFormat("hh:mm a")
    return timeFormat.format(Date(data.timestamp.toLong()))
}
fun getDateFormat(data: conversations):String{
    val dateFormat = SimpleDateFormat("MMM dd, yyyy")
    return dateFormat.format(Date(data.timestamp.toLong()))
}


 fun getNameSpannable(name: String?): Spannable {
    name?.let {
        val str = SpannableStringBuilder(name)
        val s = name.split("with")
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            s[0].length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        if (s.size > 1)
            str.setSpan(
                StyleSpan(Typeface.BOLD),
                s[0].length + 5,
                str.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        return str
    }
    return SpannableString("")
}

fun Context.showAlertDialog(mess: String?,positiveListener:DialogInterface.OnClickListener){
    AlertDialog.Builder(this, R.style.DialogTheme).apply {
        setMessage(mess)
        setPositiveButton(R.string.ok,positiveListener)
        setNegativeButton(R.string.cancel,null)
    }.show()
}



fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager: InputMethodManager = activity.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText()) {
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,
            0
        )
    }

}


fun parseDateToddMMyyyy(time: String?): String? {
    val inputPattern = "yyyy-MM-dd HH:mm:ss"
    val outputPattern = "dd-MMM-yyyy h:mm a"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    var date: Date? = null
    var str: String? = null
    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)

    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}
fun localTimeFormet(string: String):String{
    val dateStr = "Jul 16, 2013 12:08:59 AM"
    val df = SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.ENGLISH)
    df.timeZone = TimeZone.getTimeZone("UTC")
    val date = df.parse(string)
    df.timeZone = TimeZone.getDefault()
    val formattedDate = df.format(date)
    return formattedDate
}
fun setupUI(view: View, userLoginActivity: Any) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { v, event ->
            hideSoftKeyboard(userLoginActivity as Activity)
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView, userLoginActivity)
        }
    }
}



