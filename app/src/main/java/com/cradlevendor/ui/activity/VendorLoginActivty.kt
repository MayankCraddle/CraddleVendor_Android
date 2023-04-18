package com.cradlevendor.ui.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.chat.model.MyUser

import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.R
import com.cradlevendor.databinding.ActivityVendorLoginBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.MyHelper
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_vendor_login.*
import kotlinx.android.synthetic.main.logout_dialog.*
import org.json.JSONObject


class VendorLoginActivty : BaseActivity(), View.OnClickListener{

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityVendorLoginBinding
    private var mpref: SharaGoPref? = null
    var passwordNotVisible = 0
    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var sharaGoPref: SharaGoPref? = null
    private var progress: ProgressDialog? = null
    private var name : String? = null
    private var encryptedId1 : String? = null
    private var emailMobile : String? = null
    private var isChat = true


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        statusBarColourChange()
    }
    private fun initUI() {
        val response = (application as ApplicationClass).repository
        mBinding = ActivityVendorLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel= ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
      //  mBinding.setVariable(BR.onOtpClick,this)
        mpref = SharaGoPref.getInstance(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        mBinding.tvForgotPass.setOnClickListener {
            startActivity(Intent(this, VendorForgotEmailActivity::class.java))
        }
        FirebaseApp.initializeApp(this@VendorLoginActivty)

        initFirebase()
        getFcmToken()
        onClick()
        apiResult()
    }
    private fun getFcmToken(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if (result != null)

                mpref?.setFcmKey(result.toString())
        }
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                Utility.UID = user?.uid.toString()
                // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid())
            } else {
                // Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }
    }



    private  fun initNewUserInfo() {
        val newUser = MyUser()
       /* newUser.email = user?.email
        newUser.name = name*/
        newUser.emailMobile=emailMobile
        newUser.name=name
        newUser.fcmKey=mpref!!.getFcmKey("")
        Log.e("newuser",newUser.toString())
        Log.e("getEncryptedId",SharaGoPref.getInstance(this).getEncryptedId("").toString())
        FirebaseDatabase.getInstance().reference.child("users/" + encryptedId1).setValue(newUser)
      //  FirebaseDatabase.getInstance().reference.child("users/" + user?.uid).setValue(newUser)
     //  saveUserInfo()

    }


    private fun updateUserInfo(){
        val hashMap = HashMap<String,Any>() //define empty hashmap
        hashMap.put("emailMobile",emailMobile!!)
        hashMap.put("name",name!!)
        hashMap.put("fcmKey",mpref!!.getFcmKey("")!!)
      /*  val newUser = MyUser()
        newUser.emailMobile=emailMobile
        newUser.name=name
        newUser.fcmKey=mpref!!.getFcmKey("")*/
        FirebaseDatabase.getInstance().reference.child("users").child(encryptedId1!!).updateChildren(hashMap).addOnCompleteListener(
            OnCompleteListener {

            })
            ?.addOnFailureListener(OnFailureListener { e -> Log.e("error", "" + e) })
      //  FirebaseDatabase.getInstance().reference.child("users").setValue(newUser)

    }

    private fun onClick() {
        submit_button.setOnClickListener(this)
        iv_user_password.setOnClickListener(this)
        tv_signUpOpen.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.submit_button -> {
                onClickVendorLogin()
             //   startActivity(Intent(this, VendorMainActivity::class.java))

            } R.id.tv_signUpOpen -> {

                startActivity(Intent(this, SignUpActivity::class.java))

            }
            R.id.iv_user_password -> {
                if (passwordNotVisible == 0) {
                    edt_pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    iv_user_password.setImageResource(R.drawable.password_show)
                    passwordNotVisible = 1
                } else {
                    edt_pass.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    iv_user_password.setImageResource(R.drawable.password_hidden)
                    passwordNotVisible = 0
                }
            }
        }
    }

    private fun onClickVendorLogin(){
        if (!Utility.isValidEmail(edtMobileNo.text!!.toString().trim { it <= ' ' })) {
            Toast.makeText(
                this@VendorLoginActivty,
                resources.getString(R.string.enter_valid_email),
                Toast.LENGTH_SHORT
            ).show()


        } else if (edt_pass.text!!.toString().trim { it <= ' ' }.length < 4) {
            Toast.makeText(
                this@VendorLoginActivty,
                resources.getString(R.string.enter_pass),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (MyHelper.isNetworkConnected(this)) {
                showLoader()
                loginApi()

            } else showToast(getString(R.string.no_internet_connection))


        }
    }

    private fun loginApi() {
        var listDataChild = JsonObject()
        listDataChild.addProperty("emailMobile", edtMobileNo.text.toString().trim())
        listDataChild.addProperty("password", edt_pass.text.toString().trim())
        listDataChild.addProperty("fcmKey", mpref!!.getFcmKey(""))
        /*listDataChild!!.addProperty("fcmKey", SharaGoPref.getInstance(this).getFcmKey(""))*/
        listDataChild.addProperty("channel", "Android")
        mViewModel.userLogin(listDataChild)

    }
    private fun apiResult(){
        mViewModel.mLoginRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{
                    dismissLoader()
                }
                is ExceptionHandler.Success->{
                    Log.d("response", it.data!!.toString())
                    val jsonObject = JSONObject(it.data.toString())
                    val token = jsonObject.optString("token")
                    val deactivated = jsonObject.optBoolean("deactivated")
                    val serverKey = jsonObject.optString("serverKey")
                    SharaGoPref.getInstance(this).setLoginToken(token)
              //      SharaGoPref.getInstance(this).setServerKey(serverKey)
                    SharaGoPref.getInstance(this).setServerKey("AAAAO_aB0Nw:APA91bEHe6LwDiFqU_Op2UTxmPP4-QHAfqfJLWv10sOr3NX8vQNqjKjp8-H-1_ktzXEF_tu5g5asfkgVBZIOOuQ0y1NGIv0t1otMv_tbOUrNC-yhV1RhUjGFEZPQd54ZBF3WpUSHE0R0")
                    if(!deactivated){
                        Log.d("response", it.data!!.toString())
                        val jsonObject = JSONObject(it.data!!.toString())
                        val token = jsonObject.optString("token")
                        val usertype = jsonObject.optString("type")
                        val encryptedId = jsonObject.optString("encryptedId")

                        // Log.d("usertype",user_type);
                        mpref!!.setLoginToken(token)
                        mpref!!.setUserType(usertype)
                        encryptedId1=jsonObject.optString("encryptedId")
                        emailMobile=jsonObject.optString("emailMobile")
                        name=jsonObject.optString("name")
                        Log.e("encryptedId1",encryptedId1.toString())
                        Log.e("encryptedId1",encryptedId1.toString())
                        mpref!!.setVideoID(encryptedId1!!)
                        mpref!!.setEmailId(emailMobile!!)
                        mpref!!.setName(name!!)
                        updateUserInfo()
                        //  findIDEmail(emailMobile!!)
                        if (!isChat){
                            //    initNewUserInfo()
                        }

                        startActivity(Intent(this, VendorMainActivity::class.java).putExtra("screen","login"))
                        finish()
                    }else{
                        deActivationDialog()
                    }

                }
                is ExceptionHandler.Error->{
                    dismissLoader()

                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }
    private fun findIDEmail(email11: String) {
        FirebaseDatabase.getInstance().reference.child("users")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (dataSnapshot.value != null) {
                        var key: String?
                        progress?.dismiss()
                        val email = ((dataSnapshot.value as HashMap<*, *>?))!!["emailMobile"] as String?
                        if (email == email11) {
                            if (isChat) {
                                showToast("ndndnddn")
                                isChat = false
                            }
                        }
                    }
                }
                override fun onChildChanged(
                    dataSnapshot: DataSnapshot,
                    s: String?
                ) {
                }
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }
    //reset account
    private fun deActivationDialog() {

        val  dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.logout_dialog)
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation1;
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))
        dialog.tvTitle.text=getString(R.string.your_account)

        dialog.rlYes.setOnClickListener {
            deActivateAccountApi()
            dialog.dismiss()
        }
        dialog.rlNo.setOnClickListener { dialog.dismiss() }

        dialog.rlLogOut.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun deActivateAccountApi(){
        /*   {
               "status":"Deleted/ Deactivated/Active",
               "password":"",
               "comment":""
           }*/
        val deAcJsonObject = JsonObject()
        deAcJsonObject.addProperty("status", "Active")
        deAcJsonObject.addProperty("password", edt_pass.text.toString().trim())
        deAcJsonObject.addProperty("comment", "")
        Log.e("json",deAcJsonObject.toString())
        mViewModel.changeUserAccountStatusParam(mpref!!.getLoginToken("")!!,deAcJsonObject)

        mViewModel.lchangeUserAccountStatus.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {

                }
                is ExceptionHandler.Success -> {

                    try {
                        //Log.e("TrackOrder>>>", it.data!!.toString())
                        if (it.data != null) {
                            val jsonObject = JSONObject(it.data.toString())
                            loginApi()
                            apiResult()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                is ExceptionHandler.Error -> {

                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }
    }
}