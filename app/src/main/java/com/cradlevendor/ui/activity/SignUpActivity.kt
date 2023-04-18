package com.cradlevendor.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivitySignupBinding
import com.cradlevendor.intarfaces.ItemClickListner
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.adapter.CityAdapter
import com.cradlevendor.ui.adapter.StateAdapter
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.*
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import kotlinx.android.synthetic.main.activity_vendor_forgot_email.*
import kotlinx.android.synthetic.main.dialog_state.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class SignUpActivity : BaseActivity(), View.OnClickListener, ItemClickListner {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivitySignupBinding
    private lateinit var stateAdapter: StateAdapter
    private lateinit var cityAdapter: CityAdapter
    var stateList: List<String?>? = null
    var cityList: ArrayList<String?>? = ArrayList()
    var data: OrderListItem?=null
    private var orderID: String? = null
    var stateName:String?=null
    var cityName:String?=null
    var apihit:Boolean=false
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        viewState.setOnClickListener {
            stateDialog()
        }

        initUI()
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDp5okeVVuObd6R0GZyNBZNGy8dEEv-NR4");
        }
    }

    private fun initUI() {
        img_sign_up.setOnClickListener {
            validationSignUp()
        }
        tvOpenAddress.setOnClickListener {
         //   onSearchCalled()
           // adressAutSuggtion()

        }
        tv_signInBack.setOnClickListener { finish() }

        viewCity.setOnClickListener {
            if(stateName!=null)
            /*  openCityListApi()*/
                cityDialog()
            else
                showToast("Please first select your state")

        }
        openAllState()

    }

    fun adressAutSuggtion(){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }




    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    fun validationSignUp() {

        if (editFirstName.text.toString().isEmpty()) {
            showToast(getString(R.string.first_name))
        } else if (editLastName.text.toString().isEmpty()) {
            showToast(getString(R.string.last_name))

        } else if (et_email.text.toString().trim().isEmpty()) {
            showToast( getString(R.string.emai_invalid))

        }
        else if (!Utility.isValidEmail(et_email.text.toString().trim { it <= ' ' })) {
          showToast( getString(R.string.emai_invalid))

        } else  if (et_phone.text.toString().trim().isEmpty()) {
         showToast( getString(R.string.enter_phone_no))

        }else  if (et_phone.text!!.length <11) {
         showToast( getString(R.string.enter_valid_phone))

        } else  if (editState.text.toString().isEmpty()) {
            showToast( getString(R.string.enter_state))
        }
        else if (editCity.text.toString().trim().isEmpty()) {
            showToast( getString(R.string.enter_city))
        }
        else if (editAddrss.text.toString().isEmpty()) {

            showToast( getString(R.string.enter_address))

        }
        /*else  if (etLandMark.text.toString().isEmpty()) {
            showToast( getString(R.string.enter_landmark))
        }*/

       else if (!Utility.isValidPass(et_pass.text.toString().trim { it <= ' ' })) {
            showToast( getString(R.string.pass_validation))
            return
        }
        else if(et_pass.text.toString().trim().isEmpty()){
            showToast( getString(R.string.pass_validation))
        }

     else   if ( !et_pass.text.toString().equals(et_conPass.text.toString())) {
            showToast(getString(R.string.pass_match))

        }else{
          //  apihit=true
            val mainJsonObject = JsonObject()
            val requestPersonalDetailUser = JsonObject()
            requestPersonalDetailUser.addProperty("firstName", editFirstName.text.toString())
            requestPersonalDetailUser.addProperty("lastName", editLastName.text.toString())
            requestPersonalDetailUser.addProperty("phone", et_phone.text.toString())
            requestPersonalDetailUser.addProperty("streetAddress", editAddrss.text.toString().trim())
            requestPersonalDetailUser.addProperty("country", "Nigeria")
            requestPersonalDetailUser.addProperty("state", stateName)
            requestPersonalDetailUser.addProperty("city", cityName)
            requestPersonalDetailUser.addProperty("zipcode", "")
            requestPersonalDetailUser.addProperty("landmark", "")
            requestPersonalDetailUser.addProperty("addressType", "HOME")
            requestPersonalDetailUser.addProperty("email", et_email.text.toString())
            mainJsonObject.add("metaData", requestPersonalDetailUser)
            validateAddressAPi(mainJsonObject)
        /*    {
                "metaData":{
                "firstName" : "user",
                "lastName" : "Name",
                "phone" : "8186782626",
                "streetAddress" : "6 Mobolaji Bank Anthony Way",
                "country" : "Nigeria",
                "state" : "Lagos",
                "city" : "Ikeja",
                "zipcode" : "201301",
                "landmark" : "Near Park",
                "addressType":"HOME",
                "email":"nmshmnglk421@gmail.com"
            }
            }*/



        }
    }

    override fun onClick(p0: View?) {

    }
    private var dialogState: Dialog? = null
    private fun stateDialog() {
        dialogState = Dialog(this)
        dialogState!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogState!!.setContentView(R.layout.dialog_state)
        dialogState!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogState!!.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))

        val  linearLayoutManager= LinearLayoutManager(this)
        dialogState!!.recycler_state.layoutManager = linearLayoutManager
        stateAdapter= StateAdapter(this, stateList as ArrayList<String?>?,this)
        dialogState!!.recycler_state.adapter=stateAdapter
        dialogState!!.setCanceledOnTouchOutside(true)
        dialogState!!.llCloseDialog.setOnClickListener { dialogState!!.dismiss() }
      //   dialogState!!.country_search.queryHint="State"
        //  onSearchCountry()
        onSearchCountry()
        dialogState!!.show()
    }
    private var dialogCity: Dialog? = null
    private fun cityDialog() {
        dialogCity = Dialog(this)
        dialogCity!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogCity!!.setContentView(R.layout.dialog_state)
        dialogCity!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialogCity!!.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))

        val  linearLayoutManager= LinearLayoutManager(this)
        dialogCity!!.recycler_state.layoutManager = linearLayoutManager
        cityAdapter= CityAdapter(this, cityList as ArrayList<String?>?,this)
        dialogCity!!.recycler_state.adapter=cityAdapter
        dialogCity!!.setCanceledOnTouchOutside(true)
        onSearchCity()

        dialogCity!!.llCloseDialog.setOnClickListener {

            dialogCity!!.dismiss() }
        dialogState!!.country_search.queryHint="City"
        //  onSearchCountry()

        dialogCity!!.show()
    }
    private fun openAllState(){
        //Api hit
        mViewModel.stateList()

        //Api result
        mViewModel.lStateParam.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    //   activityUserProductListInCountryBinding.recyclerLocalProduct.visibility=View.VISIBLE
                    it.data?.let {

                        dismissLoader()

                        val mCountry=ArrayList<String>()
                        // countryList=it.countryColorCodeList
                        for ( i in 0 until it.list!!.size)  {

                            mCountry.add(it.list[i]!!)
                        }
                        stateList=mCountry


                    }
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }
    fun onSearchCountry(){
        dialogState!!.country_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.toString())
                //  countryListAdapter!!.filter.filter(newText)
                return false
            }

        })
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        var filterdNames: ArrayList<String?>? = ArrayList()

        //looping through existing elements
        for (s in this.stateList!!) {
            //if the existing elements contains the search input
            if (s!!.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames!!.add(s)
            }
        }
        filterdNames = stateAdapter.filterList(filterdNames)

    }

    fun onSearchCity(){
        dialogCity!!.country_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCity(newText.toString())
                //  countryListAdapter!!.filter.filter(newText)
                return false
            }

        })
    }
    private fun filterCity(text: String) {
        //new array list that will hold the filtered data
        var filterdNames: ArrayList<String?>? = ArrayList()

        //looping through existing elements
        for (s in this.cityList!!) {
            //if the existing elements contains the search input
            if (s!!.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames!!.add(s)
            }
        }
        filterdNames = cityAdapter.filterCity(filterdNames)

    }

    override fun onClickItem(position: Int, requestcode: Int,name:String) {
        if(MyConstants.STATE_REQ_CODE==requestcode){
            stateName=name
            mBinding.editState.setText(name)
           mBinding.editCity.setText(null)
            dialogState!!.dismiss()
            openCityListApi()

            dialogState!!.dismiss()
        }
        if(MyConstants.CITY_REQ_CODE==requestcode){
               cityName=name
            mBinding.editCity.setText(name)
            dialogCity!!.dismiss()
        }
    }

    private fun openCityListApi(){
        //Api hit
        mViewModel.cityListParam(stateName!!)

        //Api result
        mViewModel.lCityList.observe(this){
            when(it){
                is ExceptionHandler.Loading->{}
                is ExceptionHandler.Success->{
                    dismissLoader()
                    //   activityUserProductListInCountryBinding.recyclerLocalProduct.visibility=View.VISIBLE
                    it.data?.let {

                        dismissLoader()

                        val mCountry=ArrayList<String>()
                        cityList!!.clear()
                        // countryList=it.countryColorCodeList
                        for ( i in 0 until it.list!!.size)  {

                            mCountry.add(it.list[i]!!)
                        }
                        cityList!!.addAll(mCountry)
                        /* if(isShowing==false){
                             dialogCity = Dialog(this)
                         }*/



                    }
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }
    private fun validateAddressAPi(jsonObject: JsonObject){
     //   showLoader()
        mViewModel.validateApi(jsonObject)
        // showLoader()

        mViewModel.lValidateAddressRequest.observe(this){
            when(it){
                is ExceptionHandler.Loading->{
                    dismissLoader()
                }
                is ExceptionHandler.Success->{
                    dismissLoader()
                    /* {
                         "otpId" : "11qohOt3qm5YlaTFeutSqCoUXfSqeNyh8","message":"otp resent successfully!"
                     }*/
                    mViewModel.lValidateAddressRequest.removeObservers(this);

                    lifecycleScope.launch{
                    Log.d("response", it.data!!.toString())
                    val jsonObject = JSONObject(it.data.toString())

                    if(jsonObject.optBoolean("success")){
                     val addressCode = jsonObject.optString("addressCode")
                        Log.e("addressCode",addressCode)
                        val mJsonObject = Intent(this@SignUpActivity, UploadDocumentActivity::class.java)
                        mJsonObject.putExtra("emailMobile", et_email.text.toString())
                        mJsonObject.putExtra("firstName", editFirstName.text.toString())
                        mJsonObject.putExtra("lastName", editLastName.text.toString())
                        mJsonObject.putExtra("email", et_email.text.toString())
                        mJsonObject.putExtra("mobile", "+234"+et_phone.text.toString())
                        mJsonObject.putExtra("password", et_pass.text.toString())
                        mJsonObject.putExtra("addressCode", addressCode)
                        mJsonObject.putExtra("country", "Nigeria")
                        mJsonObject.putExtra("state", stateName)
                        mJsonObject.putExtra("city", cityName)
                        mJsonObject.putExtra("streetAddress", editAddrss.text.toString().trim())
                        mJsonObject.putExtra("fcmKey",SharaGoPref.getInstance(this@SignUpActivity).getFcmKey(""))
                        startActivity(mJsonObject)

                    }
                    }
                }
                is ExceptionHandler.Error->{
                    dismissLoader()
                    mViewModel.lValidateAddressRequest.removeObservers(this);
                    lifecycleScope.launch{
                        Utility.toastMessage(this@SignUpActivity,it.errorMessage)
                    }


                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                     //   Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                      //  Log.i(TAG, status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onSearchCalled() {
        // Set the fields to specify which types of place data to return.
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        ).setCountry("NG") //NIGERIA
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }


}