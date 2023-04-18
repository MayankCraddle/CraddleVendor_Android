package com.cradlevendor.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityEditProfileBinding
import com.cradlevendor.ui.common_fragment.LoaderFragment
import com.cradlevendor.ui.model.AddressListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.MyConstants
import com.cradlevendor.utils.PermissionKeys
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class EditProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityEditProfileBinding
    val PERMISSION_REQUEST_CODE = 10001


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initialiseUI()
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    private fun initialiseUI() {
        val response = (application as ApplicationClass).repository
        mBinding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        mViewModel.detailByTokenParam(SharaGoPref.getInstance(this).getLoginToken("").toString())
        getApiResult()
        btn_updateProfile.setOnClickListener(this)
        getIntentData()
        rl_user_profile.setOnClickListener(this)

    }
    private fun apiHit(jsonObject: JsonObject) {

        mViewModel.updateProfileParam(SharaGoPref.getInstance(this).getLoginToken("").toString(),jsonObject)
    }
    var from:String?=null
    var id=""
    var data: AddressListItem?=null
    private fun getIntentData(){
        //   data=intent.getParcelableExtra("data")
        val firstName=intent.getStringExtra("first_name").toString()
        val last_name=intent.getStringExtra("last_name").toString()
        val emailID=intent.getStringExtra("email_id").toString()


        tv_usertoolbartitle.text="Update Profile"
        edtFirstName.setText(firstName)
        edtLastName.setText(last_name)
        edtEmailPhoneNo.setText(emailID)

        iv_back.setOnClickListener {
            finish()
        }
        rl_cus_toolbar.setBackgroundColor(SharaGoPref.getInstance(this).getColor(0)!!)


    }
    var emaild_id: String?=null
    var first_name: String?=null
    var last_name: String?=null
    var street_address: String?=null
    var state: String?=null
    var country: String?=null
    var city: String?=null
    var zipcode: String?=null
    var landmark: String?=null
    var userIamge=""
    private fun getApiResult(){
        mViewModel.lUpdateProfile.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    it.data?.let {
                        dismissLoader()
                        showToast("Update Profile")
                        finish()

                    }
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
        mViewModel.luploadSingleFile.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    try {
                        val jsonObject = JSONObject(it.data!!.toString())
                        userIamge = jsonObject.optString("file")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
        mViewModel.lDetailByToken.observe(this){ it ->
            when(it){
                is ExceptionHandler.Loading->{
                    LoaderFragment.dismissLoader(this.supportFragmentManager)
                }
                is ExceptionHandler.Success->{
                    LoaderFragment.dismissLoader(this.supportFragmentManager)
                    it.data?.let {
                        Log.e("profile",it.toString())
                        emaild_id=it.emailMobile.toString()

                        /* if (it.metaData!!.image!=null){
                             Glide.with(requireActivity()).load(MyConstants.file_Base_URL_flag+it.metaData!!.image).into(mBinding.ivUserProfile)
                         }*/
                        try {
                            if (it.metaData!=null){

                               first_name= it.metaData.firstName
                               mBinding.editCompany.setText(it.metaData.companyName)
                               last_name= it.metaData.lastName
                               street_address= it.metaData.streetAddress
                               country= it.metaData.country
                               state= it.metaData.state
                               city= it.metaData.city
                               zipcode= it.metaData.zipcode
                               landmark= it.metaData.landmark
                               mBinding.tvCountry.text= "Nigeria"
                                Glide.with(this).load(MyConstants.file_Base_URL+ it.metaData.image).error(
                                    R.drawable.avatar).into(mBinding.ivUserProfile)



                                /*    Glide.with(this).load(MyConstants.file_Base_URL+ it.metaData.image).error(
                                        R.drawable.avatar).into(mBinding.ivUserProfile)*/
                            }

                        }catch (e:Exception){

                        }
                    }

                }
                is ExceptionHandler.Error->{
                    LoaderFragment.dismissLoader(this.supportFragmentManager)

                }
            }
        }
    }
    private fun checkVAlidation(): Boolean {
        if (edtFirstName.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.a_first_name))
            return false
        } else if (edtLastName.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.a_last_name))
            return false
        }
        else if (edtEmailPhoneNo.text.toString().trim().isEmpty()) {
            showToast(getString(R.string.a_mobile_email_id))
            return false
        }
        /* else if (countryName!!.isEmpty()) {
               showToast(getString(R.string.a_country))
               return false
           }*/
        return true
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_updateProfile -> {
                if (checkVAlidation()) {


                    var params= JsonObject()
                    params.addProperty("firstName", edtFirstName.text.toString().trim())
                    params.addProperty("lastName", edtLastName.text.toString().trim())
                    params.addProperty("streetAddress", street_address)
                    params.addProperty("companyName", edit_company.text.toString().trim())
                    params.addProperty("country", country)
                    params.addProperty("state", state)
                    params.addProperty("city", city)
                    params.addProperty("zipcode", zipcode)
                    params.addProperty("landmark", landmark)
                    params.addProperty("image",userIamge)
                    var mainjson= JsonObject()
                    mainjson.add("metaData",params)
                    Log.e("param",mainjson.toString())
                    apiHit(mainjson)
                }


            }  R.id.rl_user_profile -> {
            if(PermissionKeys.checkPermission1(this)){
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .start()
            }else
                requestPermission()
        }
        }

    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            val imageFile = File(getPath(uri)!!)
            // Use Uri object instead of File to avoid storage permissions
            iv_user_profile.setImageURI(uri)
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
        Log.e("bodydata",imageFile.toString())
        mViewModel.uploadSingleFile(bodyList)
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
}