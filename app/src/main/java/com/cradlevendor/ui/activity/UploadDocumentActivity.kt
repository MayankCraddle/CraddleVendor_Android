package com.cradlevendor.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityDocumentBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.adapter.DocumentAdapter
import com.cradlevendor.ui.model.ListItem
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.PermissionKeys
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_document.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class UploadDocumentActivity : BaseActivity(), DocumentAdapter.DocumentItem, View.OnClickListener {

    private lateinit var userIamge: String
    private lateinit var fileName: String
    lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityDocumentBinding
    var data: OrderListItem? = null
    private var docID: String? = null
    private var docName: String? = null
    private var orderID: String? = null
    var jsonDocArray = JsonArray()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        initUI()
    }

    private fun initUI() {
        mBinding.ivBack.setOnClickListener { onBackPressed() }
        documentApi()
        mBinding.rlSubmit.setOnClickListener(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun documentApi() {
        mViewModel.documentParam()
        mViewModel.lDocument.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {

                }
                is ExceptionHandler.Success -> {
                    if (it.data!!.list!!.isNotEmpty()) {

                        mBinding.documentAdapter = DocumentAdapter(this, it.data.list, this)

                    } else {
                        /*   mBinding.recyclerReceivedOrder.visibility= View.GONE

                        mBinding.ivNoDataFound.visibility= View.VISIBLE
                        mBinding.llNoDataFound.visibility= View.VISIBLE*/
                    }
                }
                is ExceptionHandler.Error -> {

                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }

    }

    private fun statusBarColourChange() {
    }

    override fun clickItem(data: ListItem) {

        if (PermissionKeys.checkPermission(this)) {
            docID = data.id.toString()
            docName = data.name
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .start()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            val imageFile = File(getPath(uri)!!)
            // Use Uri object instead of File to avoid storage permissions
            //   iv_user_profile.setImageURI(uri)

            showLoader()
            uploadLogoApi(imageFile)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadLogoApi(imageFile: File) {
        val bodyList = ArrayList<MultipartBody.Part>()
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile!!)
        val bodydata = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
        bodyList.add(bodydata)
        Log.e("bodydata", imageFile.toString())
        mViewModel.uploadSingleFile(bodyList)
        mViewModel.luploadSingleFile.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {}
                is ExceptionHandler.Success -> {
                    dismissLoader()
                    try {
                        val jsonObject1 = JSONObject(it.data!!.toString())
                        fileName = jsonObject1.optString("file")
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("type", docID)
                        jsonObject.addProperty("document", fileName)
                        jsonDocArray.add(jsonObject)
                        tvTitle.text = "Uploaded"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is ExceptionHandler.Error -> {
                    dismissLoader()
                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }
    }

    fun getPath(uri: Uri?): String? {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = this!!.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val path = cursor.getString(column_index)
            cursor.close()
            return path
        }
        // this is our fallback here
        return uri.path
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rlSubmit -> {

                val mJsonObject = JsonObject()
                mJsonObject.addProperty("emailMobile", intent.getStringExtra("emailMobile"))
                mJsonObject.addProperty("email", intent.getStringExtra("email"))
                mJsonObject.addProperty("mobile", intent.getStringExtra("mobile"))
                mJsonObject.addProperty("password", intent.getStringExtra("password"))
                mJsonObject.addProperty("country", intent.getStringExtra("country"))
                mJsonObject.addProperty("fcmKey", intent.getStringExtra("fcmKey"))
                mJsonObject.addProperty("addressCode", intent.getStringExtra("addressCode"))
                Log.e("addressCode1",intent.getStringExtra("addressCode").toString())

                val requestPersonalDetailUser = JsonObject()
                requestPersonalDetailUser.addProperty(
                    "firstName",
                    intent.getStringExtra("firstName")
                )
                requestPersonalDetailUser.addProperty("lastName", intent.getStringExtra("lastName"))
                requestPersonalDetailUser.addProperty("country", "Nigeria")
                requestPersonalDetailUser.addProperty("city", intent.getStringExtra("city"))
                requestPersonalDetailUser.addProperty("state", intent.getStringExtra("state"))
                requestPersonalDetailUser.addProperty("zipcode", "")
                requestPersonalDetailUser.addProperty("streetAddress", intent.getStringExtra("streetAddress"))
                requestPersonalDetailUser.add("documents", jsonDocArray)
                mJsonObject.add("metaData", requestPersonalDetailUser)
                Log.e("mjson", mJsonObject.toString())

                val intent = Intent(this, SignupAccountDetailActivity::class.java)
                intent.putExtra("json",mJsonObject.toString())
                startActivity(intent)

                // apiHit(mJsonObject)
            }

        }
    }
    private fun apiHit(jsonObject: JsonObject) {
        showLoader()
        mViewModel.userSignup(jsonObject)
        mViewModel.mLiveDataUserSignUpRequest.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {
                    dismissLoader()
                }

                is ExceptionHandler.Success -> {
                    dismissLoader()
                    /*{"message":"User Already Exist!","code":208,"success":false}*/
                    val jsonObject = JSONObject(it.data!!.toString())
                    Log.e("SignUP", jsonObject.toString())
                    val success = jsonObject.optString("success")
                    if (success.equals("false")) {
                        showToast("User Already Exist")
                    } else {
                        startActivity(
                            Intent(this, OtpActivity::class.java).putExtra(
                                "otpId",
                                jsonObject.optString("otpId")
                            )
                        )
                        finish()

                    }

                }
                is ExceptionHandler.Error -> {
                    dismissLoader()
                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }
    }

}