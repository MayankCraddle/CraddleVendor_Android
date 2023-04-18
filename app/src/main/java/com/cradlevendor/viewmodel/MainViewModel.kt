package com.cradlevendor.viewmodel
import android.content.Context
import androidx.lifecycle.*
import com.cradlevendor.R
import com.google.gson.JsonObject
import com.cradlevendor.repository.QuoteRepository
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.model.*
import com.cradlevendor.utils.Utility

import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(private val repository: QuoteRepository):ViewModel() {

    private val logInResult = MutableLiveData<String>()
    private val mResult = MutableLiveData<String>()

    private val mLLoginRequest:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val mLoginRequest:LiveData<ExceptionHandler<JsonObject>> = mLLoginRequest

    private val mLForgotPassRequest:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lForgotPassRequest:LiveData<ExceptionHandler<JsonObject>> = mLForgotPassRequest

    private val mLNewPass:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lNewPass:LiveData<ExceptionHandler<JsonObject>> = mLNewPass

    private val mLDetailByToken:MutableLiveData<ExceptionHandler<UserDetailsResponse>> = MutableLiveData()
    val lDetailByToken:LiveData<ExceptionHandler<UserDetailsResponse>> = mLDetailByToken

    private val mLUpdateProfile:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lUpdateProfile:LiveData<ExceptionHandler<JsonObject>> = mLUpdateProfile




    //USER SIGN UP SCREEN
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var phone_no: String = ""
    var state: String = ""
    var address: String = ""
    var landmark: String = ""
    var city: String = ""
    var country: String = ""
    var busName: String = ""
    var pass: String = ""
    var conPass: String = ""
    var busAdd: String = ""
    private var passMatch: String = ""
    private var conPassMatch: String = ""

    fun getUserSignUpResult(): LiveData<String> = mResult
    fun performValidationSignUp(context: Context) {

        if (firstName.isEmpty()) {
            mResult.value = context.getString(R.string.first_name)
            return
        }
        if (lastName.isEmpty()) {
            mResult.value = context.getString(R.string.last_name)
            return
        }
        if (!Utility.isValidEmail(email.trim { it <= ' ' })) {
            mResult.value = context.getString(R.string.emai_invalid)
            return
        }
        if (phone_no.length < 11) {

            mResult.value = context.getString(R.string.enter_phone_no)
            return
        }
        if (state.isEmpty()) {
            mResult.value = context.getString(R.string.enter_state)
            return
        }
        if (city.isEmpty()) {
            mResult.value = context.getString(R.string.enter_city)
            return
        }
        if (city.isEmpty()) {
            mResult.value = context.getString(R.string.enter_city)
            return
        }
        if (address.isEmpty()) {
            mResult.value = context.getString(R.string.enter_address)
            return
        }
        if (landmark.isEmpty()) {
            mResult.value = context.getString(R.string.enter_landmark)
            return
        }
        passMatch = pass

        if (!Utility.isValidPass(pass.trim { it <= ' ' })) {
            mResult.value = context.getString(R.string.pass_validation)
            return
        }
        /* if (conPass.equals(pass)) {
             mResult.value = context.getString(R.string.pass_validation)
             return
         }*/
        /* else if (MyHelper.isValidPassword(binding.etPassword.text.toString()))
             MyHelper.showMininumPasswordAlert(requireContext())
       */  passMatch = pass
        conPassMatch = conPass
        if (passMatch != conPassMatch) {
            mResult.value = context.getString(R.string.pass_match)
            return
        }
        mResult.value = "go"


    }
    fun userLogin(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getUserLoginRequest(jsonObject).collect { values ->
            mLLoginRequest.value = values
        }
    }
    //FORGOT PASSWORD API HIT
    fun vendorForgotPassVeri(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getForgotPassRequest(jsonObject).collect { values ->
            mLForgotPassRequest.value = values
        }
    }

    fun newPass(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getNewPassRequest(jsonObject).collect { values ->
            mLNewPass.value = values
        }
    }

    fun detailByTokenParam(token: String) = viewModelScope.launch {
        repository.detailByTokenReq(token).collect { values ->
            mLDetailByToken.value = values
        }
    }

    fun updateProfileParam(token: String,jsonObject: JsonObject) = viewModelScope.launch {
        repository.updateProfileReq(token,jsonObject).collect { values ->
            mLUpdateProfile.value = values
        }
    }
    private val mLuploadSingleFile:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val luploadSingleFile:LiveData<ExceptionHandler<JsonObject>> = mLuploadSingleFile

    fun uploadSingleFile(list: List<MultipartBody.Part>)= viewModelScope.launch {
        repository.uploadSingleFileReq(list).collect { values ->
            mLuploadSingleFile.value = values
        }
    }

    //CHANGE PASS WITH LOGIN  API HIT
    private val mLChangePassWithLogin:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lChangePassWithLogin:LiveData<ExceptionHandler<JsonObject>> = mLChangePassWithLogin

    fun changePassWithLogin(token: String,jsonObject: JsonObject) = viewModelScope.launch {
        repository.chanPassWithLoginRequest(token,jsonObject).collect { values ->
            mLChangePassWithLogin.value = values
        }
    }

    private val mLChangeOrderState:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lChangeOrderState:LiveData<ExceptionHandler<JsonObject>> = mLChangeOrderState

    fun changeOrderState(token: String,orderID:String,jsonObject: JsonObject) = viewModelScope.launch {
        repository.changeOrderStateReq(token,orderID,jsonObject).collect { values ->
            mLChangeOrderState.value = values
        }
    }


    private val mLCancelOrder:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lCancelOrder:LiveData<ExceptionHandler<JsonObject>> = mLCancelOrder

    fun cancelOrder(token: String,orderID:String,jsonObject: JsonObject) = viewModelScope.launch {
        repository.cancelOrderReq(token,orderID,jsonObject).collect { values ->
            mLChangeOrderState.value = values
        }
    }

    private val mLcurrentOrder:MutableLiveData<ExceptionHandler<CurrentOrderVendorResponse>> = MutableLiveData()
    val lcurrentOrder:LiveData<ExceptionHandler<CurrentOrderVendorResponse>> = mLcurrentOrder

    fun currentOrderStateParam(token: String) = viewModelScope.launch {
        repository.currentOrderReq(token).collect { values ->
            mLcurrentOrder.value = values
        }
    }
    private val mLProgressOrder:MutableLiveData<ExceptionHandler<CurrentOrderVendorResponse>> = MutableLiveData()
    val lProgressOrder:LiveData<ExceptionHandler<CurrentOrderVendorResponse>> = mLProgressOrder

    fun processingOrderStateParam(token: String) = viewModelScope.launch {
        repository.inOrderInProgressReq(token).collect { values ->
            mLProgressOrder.value = values
        }
    }
    private val mLcancelledOrder:MutableLiveData<ExceptionHandler<CurrentOrderVendorResponse>> = MutableLiveData()
    val lcancelledOrder:LiveData<ExceptionHandler<CurrentOrderVendorResponse>> = mLcancelledOrder

    fun cancelledOrderParam(token: String) = viewModelScope.launch {
        repository.cancelOrderReq(token).collect { values ->
            mLcancelledOrder.value = values
        }
    }
    private val mLDeliveredOrder:MutableLiveData<ExceptionHandler<CurrentOrderVendorResponse>> = MutableLiveData()
    val lDeliveredOrder:LiveData<ExceptionHandler<CurrentOrderVendorResponse>> = mLDeliveredOrder

    fun deliveredOrderParam(token: String) = viewModelScope.launch {
        repository.inOrderInProgressReq(token).collect { values ->
            mLProgressOrder.value = values
        }
    }

    private val mLlogOut:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lLogOut:LiveData<ExceptionHandler<JsonObject>> = mLlogOut

    fun logoutParam(token: String) = viewModelScope.launch {
        repository.logOutReq(token).collect { values ->
            mLlogOut.value = values
        }
    }

    private val mLDocument:MutableLiveData<ExceptionHandler<DocumentListResponse>> = MutableLiveData()
    val lDocument:LiveData<ExceptionHandler<DocumentListResponse>> = mLDocument

    fun documentParam() = viewModelScope.launch {
        repository.documentReq().collect { values ->
            mLDocument.value = values
        }
    }
    private val mMutableUserSignUpRequest: MutableLiveData<ExceptionHandler<JsonObject>> =
        MutableLiveData()
    val mLiveDataUserSignUpRequest: LiveData<ExceptionHandler<JsonObject>> =
        mMutableUserSignUpRequest

    fun userSignup(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getUserSignUpRequest(jsonObject).collect { values ->
            mMutableUserSignUpRequest.value = values
        }
    }
    private val mutableOtpUserRequest: MutableLiveData<ExceptionHandler<JsonObject>> =
        MutableLiveData()
    val liveOtpUserRequest: LiveData<ExceptionHandler<JsonObject>> = mutableOtpUserRequest

    //OTP verify of user when sign up
    fun otpSignUP(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getUserOtpRequest(jsonObject).collect { values ->
            mutableOtpUserRequest.value = values
        }
    }
    private val mLChangeUserAccountStatus: MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lchangeUserAccountStatus: LiveData<ExceptionHandler<JsonObject>> = mLChangeUserAccountStatus
    fun changeUserAccountStatusParam(token: String, jsonObject: JsonObject) = viewModelScope.launch {
        repository.changeUserAccountStatusReq(token, jsonObject).collect { values ->
            mLChangeUserAccountStatus.value = values
        }
    }

    private val mAccountDetailList:MutableLiveData<ExceptionHandler<AccountDetailListResponse>> = MutableLiveData()
    val lAccountDetailList:LiveData<ExceptionHandler<AccountDetailListResponse>> = mAccountDetailList

    fun accountDetailListParam(token: String) = viewModelScope.launch {
        repository.bankAccountListReq(token).collect { values ->
            mAccountDetailList.value = values
        }
    }
    private val mUserMarkAsDefaultParam:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val luserMarkAsDefaultParam:LiveData<ExceptionHandler<JsonObject>> = mUserMarkAsDefaultParam

    fun userMarkAsDefault(token: String,id:String) = viewModelScope.launch {
        repository.userMarkAsDefaultReq(token,id).collect { values ->
            mUserMarkAsDefaultParam.value = values
        }
    }
    private val mLDeleteAccountParam:MutableLiveData<ExceptionHandler<JsonObject>> = MutableLiveData()
    val lDeleteAccountParam:LiveData<ExceptionHandler<JsonObject>> = mLDeleteAccountParam

    fun deleteAccount(token: String,id:String) = viewModelScope.launch {
        repository.deleteAccountReq(token,id).collect { values ->
            mLDeleteAccountParam.value = values
        }
    }

    private val mMutableAddBankAccountRequest: MutableLiveData<ExceptionHandler<JsonObject>> =
        MutableLiveData()
    val liveDataAddBankAccountRequest: LiveData<ExceptionHandler<JsonObject>> =
        mMutableAddBankAccountRequest

    fun addBankAccountApi(token: String,jsonObject: JsonObject) = viewModelScope.launch {
        repository.getAddBankAccountReq(token,jsonObject).collect { values ->
            mMutableAddBankAccountRequest.value = values
        }
    }
    private val mLNotificationList: MutableLiveData<ExceptionHandler<NotificationListResponse>> = MutableLiveData()
    val lNotificationList: LiveData<ExceptionHandler<NotificationListResponse>> = mLNotificationList
    fun notificationListParam(token: String,pageNumber: String,limit: String) = viewModelScope.launch {
        repository.notificationReq(token,pageNumber,limit).collect { values ->
            mLNotificationList.value = values
        }
    }

    private val mLResendOtpRequest: MutableLiveData<ExceptionHandler<JsonObject>> =
        MutableLiveData()
    val lResendOtpRequest: LiveData<ExceptionHandler<JsonObject>> = mLResendOtpRequest

    fun resendOtpReq(jsonObject: JsonObject) = viewModelScope.launch {
        repository.resendOtp(jsonObject).collect { values ->
            mLResendOtpRequest.value = values
        }
    }
    private val mLStateParam: MutableLiveData<ExceptionHandler<NewStateListResponse>> =
        MutableLiveData()
    val lStateParam: LiveData<ExceptionHandler<NewStateListResponse>> = mLStateParam

    fun stateList() = viewModelScope.launch {
        repository.stateListReq().collect { values ->
            mLStateParam.value = values
        }
    }

    private val mLCityList: MutableLiveData<ExceptionHandler<NewCityListResponse>> = MutableLiveData()
    val lCityList: LiveData<ExceptionHandler<NewCityListResponse>> = mLCityList
    fun cityListParam(stateName:String) = viewModelScope.launch {
        repository.cityListReq(stateName).collect { values ->
            mLCityList.value = values
        }
    }

    private val mLValidateAddressRequest: MutableLiveData<ExceptionHandler<JsonObject>> =
        MutableLiveData()
    val lValidateAddressRequest: LiveData<ExceptionHandler<JsonObject>> = mLValidateAddressRequest

    fun validateApi(jsonObject: JsonObject) = viewModelScope.launch {
        repository.validateAddressReq(jsonObject).collect { values ->
            mLValidateAddressRequest.value = values
        }
    }

}