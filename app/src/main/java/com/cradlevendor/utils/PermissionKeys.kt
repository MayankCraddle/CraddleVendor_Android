package com.cradlevendor.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


/**
 * Created by RAm on 7/12/16.
 */
object PermissionKeys {
    private val INTENT_REQUEST_GET_IMAGES = 13
    val gallery = 12
    const val RC_FILE_PICKER_PERM = 321

    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    //request code for permission
    const val REQUEST_CODE_ACCESS_FINE_LOCATION = 1
    const val REQUEST_CODE_ACCESS_COARSE_LOCATION = 2
    const val REQUEST_CODE_ACCESS_NETWORK_STATE = 3
    const val REQUEST_CODE_ACCESS_WIFI_STATE = 4
    const val REQUEST_CODE_CALL_PHONE = 5
    const val REQUEST_CODE_CAMERA = 7
    const val REQUEST_CODE_READ_CONTACTS = 8
    const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 9
    const val REQUEST_CODE_READ_PHONE_STATE = 10
    const val REQUEST_CODE_WAKE_LOCK = 11
    const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 12
    const val REQUEST_CODE_PERMISSION_ALL = 13

    //permission name
    const val PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val PERMISSION_ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE
    const val PERMISSION_ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE
    const val PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE
    const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    const val PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS
    const val PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
    const val PERMISSION_WAKE_LOCK = Manifest.permission.WAKE_LOCK
    const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val CALL_PHONE = Manifest.permission.CALL_PHONE
   // private var marshmallowPermission: MarshmallowPermission? = null
     var permissions = arrayOf(
        PERMISSION_CAMERA,
        PERMISSION_READ_EXTERNAL_STORAGE,
        PERMISSION_WRITE_EXTERNAL_STORAGE
    )
     fun checkPermissionREAD_EXTERNAL_STORAGE(context: Context?): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        return if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity?)!!,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    //  showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat
                        .requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                }
                false
            } else {
                true
            }
        } else {
            true
        }
    }
     fun checkPermission(context: Context): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
                return false
            }
        } else {
            return true
        }
    }
     fun requestPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", context.packageName))
                (context as Activity).startActivityForResult(intent, gallery)
            } catch (e: java.lang.Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                (context as Activity).startActivityForResult(intent, gallery)
            }
        }
    }

    /* fun captureImages(context: Context) {
         marshmallowPermission = MarshmallowPermission(context as Activity)
        if (marshmallowPermission!!.isPermissionGrantedAll(
                REQUEST_CODE_PERMISSION_ALL,
                *permissions
            )
        ) {

            val intent= Intent(context, ImagePickerDemo::class.java)
            (context).startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES)
        }
    }*/
   /* fun loadfile(context: Context) {
        pickDocClicked(context)
    }*/

  /*  @AfterPermissionGranted(RC_FILE_PICKER_PERM)
    fun pickDocClicked(context: Context) {
        if (EasyPermissions.hasPermissions(context, PERMISSIONS_FILE_PICKER)) {
            onPickDoc(context)
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(context as Activity, context.getString(R.string.rationale_doc_picker), RC_FILE_PICKER_PERM, PERMISSIONS_FILE_PICKER)
        }
    }

    fun onPickDoc(context: Context) {
        val MAX_ATTACHMENT_COUNT = 10
        val photoPaths = ArrayList<Uri>()
        val docPaths = ArrayList<Uri>()

        val zips = arrayOf("zip", "rar")
        val pdfs = arrayOf("aac")
        val maxCount: Int = MAX_ATTACHMENT_COUNT - photoPaths.size
        if (docPaths.size + photoPaths.size == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(
                context, "Cannot select more than $MAX_ATTACHMENT_COUNT items",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            FilePickerBuilder.instance
                .setMaxCount(1)
                .setSelectedFiles(docPaths)
                .setActivityTheme(R.style.FilePickerTheme)
                .setActivityTitle("Please select doc")
                //.setImageSizeLimit(5) //Provide Size in MB
              //  .setVideoSizeLimit(20) //                    .addFileSupport("ZIP", zips)
                //                    .addFileSupport("AAC", pdfs, R.drawable.pdf_blue)
                .enableDocSupport(true)
                .enableSelectAll(true)
                .sortDocumentsBy(SortingTypes.NAME)
                .withOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .pickFile(context as Activity)
        }
    }*/
  fun checkPermission1(context: Context): Boolean {
      return if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
          != PackageManager.PERMISSION_GRANTED
      ) {
          // Permission is not granted
          false
      } else true
  }


}