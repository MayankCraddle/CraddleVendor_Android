package com.cradlevendor.utils

import android.app.Activity
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


object FunctionHelper {
    private var sProgressDialog: ProgressDialog? = null

    val isDialogShowing: Boolean
        get() = sProgressDialog != null && sProgressDialog!!.isShowing

    fun disable_user_Intration(context: Context, text: String) {
        enableUserIntraction()
        if (sProgressDialog == null || sProgressDialog!!.context !== context) {
            sProgressDialog!!.setCancelable(false)
            sProgressDialog!!.setCanceledOnTouchOutside(true)
            sProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            sProgressDialog!!.setMessage(text)
            try {
                sProgressDialog!!.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
            }

        }
    }


    fun touchDisable(context: Context, flag: Boolean) {
        if (flag) {
            (context as Activity).window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            (context as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    fun enableUserIntraction() {
        try {
            if (sProgressDialog != null && sProgressDialog!!.isShowing) {
                sProgressDialog!!.dismiss()
            }
            sProgressDialog = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideKeyBoard(activity: Activity): Boolean {
        try {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun isshowKeyBoard(activity: Activity, view: View): Boolean {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun hideKeyBoard(context: Context, editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideKeyBoard(context: Context, liner: LinearLayout) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(liner.windowToken, 0)
    }

    fun hideKeyBoard(context: Context, liner: RelativeLayout) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(liner.windowToken, 0)
    }

    fun hideKeyBoard(context: Context, spinner: Spinner) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(spinner.windowToken, 0)
    }

    fun showSnackMessage(view: View, message: String) {
        try {
            //  Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessage(view: View, @StringRes message: Int) {
        try {
            //Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessage(
        view: View,
        message: String,
        actionText: CharSequence,
        listener: View.OnClickListener
    ) {
        try {
            // Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessage(
        view: View, @StringRes message: Int, @StringRes actionText: Int,
        listener: View.OnClickListener
    ) {
        try {
            //  Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessageWithAction(
        view: View, @StringRes message: Int, @StringRes actionText: Int,
        listener: View.OnClickListener
    ) {
        try {
            //  Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessage(
        view: View, @StringRes message: Int, @StringRes actionText: Int,
        listener: View.OnClickListener, @ColorInt color: Int
    ) {
        try {
            // Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener).setActionTextColor(color).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSnackMessage(
        view: View,
        message: String, @StringRes actionText: Int,
        listener: View.OnClickListener, @ColorInt color: Int
    ) {
        try {
            //Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener).setActionTextColor(color).show();
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun downloadFile(myHTTPUrl: String,context: Context) {
        try {
            val request = DownloadManager.Request(Uri.parse(myHTTPUrl))
            request.setTitle("File download")
            request.setDescription("File is being downloaded...")
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val nameOfFile: String = URLUtil.guessFileName(myHTTPUrl, null, MimeTypeMap.getFileExtensionFromUrl(myHTTPUrl))
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile)
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            manager!!.enqueue(request)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun shareFile(context: Context, file:String){
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
        share.putExtra(Intent.EXTRA_TEXT, file)
        context.startActivity(Intent.createChooser(share, "Share link!"))
    }

    fun getCamelCase(firstname: String): String {
        try {
            return firstname.substring(0, 1).toUpperCase() + firstname.substring(
                1,
                firstname.length
            )
        } catch (e: Exception) {
            return firstname
        }

    }
    fun getLowerCase(lowerCase: String):String{
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1).toLowerCase();
    }
    fun showToastLong(context: Context, messege: String) {
        Toast.makeText(context, messege, Toast.LENGTH_LONG).show()
    }
    fun showToastShort(context: Context, messege: String) {
        Toast.makeText(context, messege, Toast.LENGTH_SHORT).show()
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            return null
        }

    }


    fun getPid(pid: String?): String {
        return if (pid == null) {
            "P00000"
        } else if (pid.isEmpty()) {
            "P00000"
        } else if (pid.length == 1) {
            "P0000$pid"
        } else if (pid.length == 2) {
            "P000$pid"
        } else if (pid.length == 3) {
            "P00$pid"
        } else if (pid.length == 4) {
            "P0$pid"
        } else if (pid.length == 5) {
            "P$pid"
        } else {
            "P00000"
        }
    }
}