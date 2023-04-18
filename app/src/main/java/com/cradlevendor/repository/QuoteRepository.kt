package com.cradlevendor.repository
import android.content.Context
import com.google.gson.JsonObject
import com.cradlevendor.api.BaseApiResponse
import com.cradlevendor.api.RetrofitService
import com.cradlevendor.ui.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody


class QuoteRepository (private val retrofitService: RetrofitService,context: Context): BaseApiResponse(context) {

    suspend fun getUserLoginRequest(loginRequest: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.getUserLoginRequest(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getForgotPassRequest(loginRequest: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.getForgotPassApi(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNewPassRequest(loginRequest: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.newPassVerification(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun detailByTokenReq(token: String):Flow<ExceptionHandler<UserDetailsResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.detailByToken(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun updateProfileReq(token: String ,jsonObject: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.updateProfile(token,jsonObject) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun uploadSingleFileReq(list: List<MultipartBody.Part>):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.uploadSingleFile(list) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun chanPassWithLoginRequest(token: String ,jsonObject: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.chanPassWithLoginApi(token,jsonObject) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun changeOrderStateReq(token: String ,orderId:String,jsonObject: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.getChangeOrderState(token,orderId,jsonObject) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun cancelOrderReq(token: String ,orderId:String,jsonObject: JsonObject):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.getChangeOrderState(token,orderId,jsonObject) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun currentOrderReq(token: String):Flow<ExceptionHandler<CurrentOrderVendorResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.getCurrentOrder(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun bankAccountListReq(token: String):Flow<ExceptionHandler<AccountDetailListResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.getBankAccountList(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun inOrderInProgressReq(token: String):Flow<ExceptionHandler<CurrentOrderVendorResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.getInOrderInProgress(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun cancelOrderReq(token: String):Flow<ExceptionHandler<CurrentOrderVendorResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.getCancelledOrder(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun deliveredOrderReq(token: String):Flow<ExceptionHandler<CurrentOrderVendorResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.getDeliveryOder(token) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun logOutReq(token: String):Flow<ExceptionHandler<JsonObject>>{
        return flow {
            emit(safeApiCall { retrofitService.logOut(token) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun documentReq():Flow<ExceptionHandler<DocumentListResponse>>{
        return flow {
            emit(safeApiCall { retrofitService.documentApi() })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getUserSignUpRequest(loginRequest: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.getUserSignUpRequest(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getUserOtpRequest(loginRequest: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.getUsersignUpOtpVerificationRequest(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun changeUserAccountStatusReq(
        token: String,
        jsonObject: JsonObject
    ): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.changeUserAccountStatusApi(token, jsonObject) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun userMarkAsDefaultReq(
        token: String,
        id: String
    ): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.makeDefaultAccount(token, id) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteAccountReq(
        token: String,
        id: String
    ): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.deleteAccount(token, id) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAddBankAccountReq(token: String,jsonObject: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.getAddBankAccountRequest(token,jsonObject) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun notificationReq(token: String,pageNumber: String,limit: String
    ): Flow<ExceptionHandler<NotificationListResponse>> {
        return flow {
            emit(safeApiCall { retrofitService.getNotifications(token,pageNumber,limit) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun resendOtp(loginRequest: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.resendOtpApi(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun stateListReq(
    ): Flow<ExceptionHandler<NewStateListResponse>> {
        return flow {
            emit(safeApiCall { retrofitService.stateList() })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun cityListReq(stateName:String
    ): Flow<ExceptionHandler<NewCityListResponse>> {
        return flow {
            emit(safeApiCall { retrofitService.cityList(stateName) })
        }.flowOn(Dispatchers.IO)
    }
    suspend fun validateAddressReq(loginRequest: JsonObject): Flow<ExceptionHandler<JsonObject>> {
        return flow {
            emit(safeApiCall { retrofitService.validateAddress(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }
}