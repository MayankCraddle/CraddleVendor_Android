package com.cradlevendor.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.cradlevendor.R

import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object MyHelper {

    var fileUri: Uri? = null
    const val IMAGE_DIRECTORY_NAME = "Sahara-Go"

    fun isNetworkConnected(activity: Context?): Boolean {
        return if (activity != null) {
            val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.activeNetworkInfo != null
        } else false
    }

    fun isValidEmail(email: String?): Boolean {
        if (email == null)
            return false
        else
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        val EMAIL_PATTERN = "^(?=.{6,30})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(password)
        return !matcher.matches()
    }

    fun showMininumPasswordAlert(context: Context?) {
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setMessage(context.getString(R.string.password_pattern))
        builder.setPositiveButton(Html.fromHtml("<font color='#91129C'>"+context.getString(R.string.ok)+"</font>"), null)
        builder.show()
    }

    fun showCalender(textView: EditText, context: Activity?, myCalendar: Calendar) {

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel(myCalendar, textView)
            }
        val datePickerDialog = DatePickerDialog(
            context!!, R.style.DialogTheme, date, myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.datePicker.maxDate = myCalendar.timeInMillis
        datePickerDialog.show()
    }


    fun updateLabel(myCalendar: Calendar, textView: EditText) {
        val myFormat = "dd-MMM-yyyy" //In which you need put here
        val tagFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val tagsdf = SimpleDateFormat(tagFormat, Locale.US)
        textView.setText(sdf.format(myCalendar.time))
        textView.tag = tagsdf.format(myCalendar.time)
    }
    fun getOutputMediaFileUri(context: Context, i: Int): Uri? {
        return Objects.requireNonNull(getOutputMediaFile(context))?.let {
            FileProvider.getUriForFile(
                context!!, "com.family_hives.fileprovider",
                it
            )
        }
    }


    /*fun getOutputMediaFileUri(context: Context, type: Int): Uri? {
        return Objects.requireNonNull(
            if (type == 1) getOutputVideoMediaFile(context) else if (type == 3) getOutputAudioMediaFile(
                context
            ) else getOutputMediaFile(
                context
            )
        )?.let {
            FileProvider.getUriForFile(
                context,
                "com.family_hives.fileprovider",
                it
            )
        }
    }*/

    fun getOutputMediaFile(context: Context): File? {
        // External sdcard location
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            /*ContentResolver resolver = context.getContentResolver();
                 ContentValues contentValues = new ContentValues();
                 contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
                 contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                 contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                 return   resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);*/
            val mediaStorageDir =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "family_havies")

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
        } else {
            val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "family_havies"
            )

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            val mediaFile: File
            mediaFile = File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
            mediaFile
        }
    }

    fun getOutputVideoMediaFile(context: Context): File? {
        // External sdcard location
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val mediaStorageDir =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), "family_havies")

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".mp4")
        } else {
            val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "family_havies"
            )

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            val mediaFile: File
            mediaFile = File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".mp4")
            mediaFile
        }
    }

   /* fun getOutputAudioMediaFile(context: Context): File? {
        // External sdcard location
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val mediaStorageDir =
                File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "family_havies")

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            RecordAudioDialog.path =
                mediaStorageDir.path + File.separator + "Audio_" + timeStamp + ".mp3"
            File(RecordAudioDialog.path)
        } else {
            val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                "family_havies"
            )

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            RecordAudioDialog.path =
                mediaStorageDir.path + File.separator + "Audio_" + timeStamp + ".mp3"
            val mediaFile: File
            mediaFile = File(RecordAudioDialog.path)
            mediaFile
        }
    }*/

    fun getRealPathForImagesURI(contentUri: Uri, context: Activity): String? {
        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.managedQuery(
            contentUri,
            proj,  // Which columns to return
            null,  // WHERE clause; which rows to return (all rows)
            null,  // WHERE clause selection arguments (none)
            null
        ) // Order-by clause (ascending by name)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return if (!TextUtils.isEmpty(cursor.getString(column_index))) {
            cursor.getString(column_index)
        } else {
            getImagePathFromInputStreamUri(context, contentUri)
        }
    }

    fun getImagePathFromInputStreamUri(context: Activity, uri: Uri): String {
        var inputStream: InputStream? = null
        var filePath: String? = null
        var fileName: String? = ""
        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri) // context needed
                val scheme = uri.scheme
                if (scheme == "file") {
                    fileName = uri.lastPathSegment
                } else if (scheme == "content") {
                    val splitableuri = uri.toString()
                    val fileSplit = splitableuri.split("/".toRegex()).toTypedArray()
                    fileName = fileSplit[fileSplit.size - 1]
                    fileName = "$fileName.jpg"
                }
                val testFile = File(context.externalCacheDir, fileName)
                if (testFile.exists()) {
                    testFile.delete()
                }
                val photoFile = createTemporalFileFrom(context, inputStream, fileName)
                filePath = photoFile!!.absolutePath
            } catch (e: FileNotFoundException) {
                // log
            } catch (e: IOException) {
                // log
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return filePath!!
    }

    @Throws(IOException::class)
    fun createTemporalFileFrom(
        context: Activity,
        inputStream: InputStream?,
        imageFileName: String?
    ): File? {
        var targetFile: File? = null
        if (inputStream != null) {
            var read: Int
            val buffer = ByteArray(50 * 1024)
            targetFile = createTemporalFile(context, imageFileName)
            val outputStream: OutputStream = FileOutputStream(targetFile)
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return targetFile
    }

    private fun createTemporalFile(context: Context, filename: String?): File? {
        return File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
    }

    fun getVideoPathFromInputStreamUri(context: Activity, uri: Uri): String {
        var inputStream: InputStream? = null
        var filePath: String? = null
        var fileName: String? = ""
        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri) // context needed
                val scheme = uri.scheme
                if (scheme == "file") {
                    fileName = uri.lastPathSegment
                } else if (scheme == "content") {
                    val splitableuri = uri.toString()
                    val fileSplit = splitableuri.split("/".toRegex()).toTypedArray()
                    fileName = fileSplit[fileSplit.size - 1]
                    fileName = "$fileName.mp4"
                }

                // Logger.e(fileName);
                val testFile = File(context.externalCacheDir, fileName)
                if (testFile.exists()) {
                    testFile.delete()
                }
                val videoFile = createTemporalFileFrom(context, inputStream, fileName)
                filePath = videoFile!!.path
            } catch (e: FileNotFoundException) {
                // log
            } catch (e: IOException) {
                // log
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return filePath!!
    }

    fun getAudioPathFromInputStreamUri(context: Activity, uri: Uri): String {
        var inputStream: InputStream? = null
        var filePath: String? = null
        var fileName: String? = ""
        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri) // context needed
                val scheme = uri.scheme
                if (scheme == "file") {
                    fileName = uri.lastPathSegment
                } else if (scheme == "content") {
                    val splitableuri = uri.toString()
                    val fileSplit = splitableuri.split("/".toRegex()).toTypedArray()
                    fileName = fileSplit[fileSplit.size - 1]
                    fileName = "$fileName.mp3"
                }

                // Logger.e(fileName);
                val testFile = File(context.externalCacheDir, fileName)
                if (testFile.exists()) {
                    testFile.delete()
                }
                val videoFile = createTemporalFileFrom(context, inputStream, fileName)
                filePath = videoFile?.path
            } catch (e: FileNotFoundException) {
                // log
                e.printStackTrace()
            } catch (e: IOException) {
                // log
                e.printStackTrace()
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return filePath.toString()
    }
/*
    fun getProgress(context: Context?): CircularProgressDrawable? {
        val progressDrawable = CircularProgressDrawable(context!!)
        progressDrawable.strokeWidth = 5f
        progressDrawable.centerRadius = 30f
        progressDrawable.start()
        return progressDrawable
    }


    fun retriveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        return ThumbnailUtils.createVideoThumbnail(
            "picturePath",
            MediaStore.Video.Thumbnails.MINI_KIND
        );
    }

    fun showImageLoader(path: String, progress: ProgressBar) {
        progress.visibility = View.VISIBLE
    }

    fun hideImageLoader(progress: ProgressBar) {
        progress.visibility = View.VISIBLE
    }

    fun analyticReport(mContext: Context, source: String) {
        val mixpanel = MixpanelAPI.getInstance(mContext, Constaints.MAXPANEL_PROJECT_TOKEN)
        val props = JSONObject()
        props.put("source", source)
        props.put("Opted out of email", true)
        mixpanel.track(source, props)

        val firebaseAnalytics = Firebase.analytics
        val bundle = Bundle()

        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, source)
        firebaseAnalytics.logEvent(source, bundle)
    }

    public fun setChangeLanguage(mContext: Activity, selectedLanguage: String) {
        val preferences = AppSharedPreferences.getInstance(mContext)
        if (!preferences.currentLanguage.equals(selectedLanguage)) {
            AppSharedPreferences.getInstance(mContext).currentLanguage = selectedLanguage
            MyApplication.locale = Locale(selectedLanguage)
            Locale.setDefault(MyApplication.locale)
            val configuration = Configuration()
            configuration.setLocale(MyApplication.locale)
            mContext.getResources().updateConfiguration(
                configuration,
                mContext.getResources().getDisplayMetrics()
            )
            startActivity(mContext, Intent(mContext,MainActivity::class.java),null)
            ActivityCompat.finishAffinity(mContext)
        }


    }*/
}