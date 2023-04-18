package com.cradlevendor.api
import com.cradlevendor.ui.model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface RetrofitService {


    @Multipart
    @POST("saharaGo/uploadSingleFile")
    suspend fun uploadSingleFile(@Part list: List<MultipartBody.Part>): Response<JsonObject>


    //VENDOR API
    @POST("vendor/login") suspend fun getUserLoginRequest(@Body loginRequest: JsonObject):Response<JsonObject>

    @PUT("vendor/forgotPassword") suspend fun getForgotPassApi(@Body loginRequest: JsonObject):Response<JsonObject>

    @PUT("vendor/forgotPasswordVerification") suspend fun newPassVerification(@Body loginRequest: JsonObject):Response<JsonObject>

    @GET("vendor/getVendorDetailByToken")
    suspend fun detailByToken(@Header("token") token: String?): Response<UserDetailsResponse>

    @PUT("vendor/updateProfile")
    suspend  fun updateProfile(@Header("token") token: String?,@Body data: JsonObject): Response<JsonObject>

    @PUT("vendor/changePassword") suspend fun chanPassWithLoginApi(@Header("token") token: String?,@Body loginRequest: JsonObject):Response<JsonObject>

    @PUT("vendor/changeOrderState/{orderId}")
    suspend fun getChangeOrderState(@Header("token") token: String?,@Path("orderId") orderId: String,@Body data: JsonObject): Response<JsonObject>

    @PUT("vendor/changeOrderState/{orderId}")
    suspend fun cancelOder(@Header("token") token: String?,@Path("orderId") orderId: String,@Body data: JsonObject): Response<JsonObject>

    //RECEIVED ORDER API HIT
    @GET("vendor/getCurrentOrder")
  suspend  fun getCurrentOrder(@Header("token") token: String?): Response<CurrentOrderVendorResponse>
 //RECEIVED ORDER API HIT
    @GET("vendor/getAllAccounts")
  suspend  fun getBankAccountList(@Header("token") token: String?): Response<AccountDetailListResponse>

    @GET("vendor/getInProgressOrder")
   suspend fun getInOrderInProgress(@Header("token") token: String?): Response<CurrentOrderVendorResponse>

    @GET("vendor/getCancelledOrder")
    suspend fun getCancelledOrder(@Header("token") token: String?): Response<CurrentOrderVendorResponse>

    @GET("vendor/getDeliveredOrder")
   suspend fun getDeliveryOder(@Header("token") token: String?): Response<CurrentOrderVendorResponse>

   @PUT("vendor/logout")
   suspend fun logOut(@Header("token") token: String?): Response<JsonObject>

    @GET("saharaGo/getDocumentType")
    suspend  fun documentApi(): Response<DocumentListResponse>

    @POST("vendor/signUp")
    suspend fun getUserSignUpRequest(@Body loginRequest: JsonObject): Response<JsonObject>

    @POST("vendor/signUpOtpVerification")
    suspend fun getUsersignUpOtpVerificationRequest(@Body loginRequest: JsonObject): Response<JsonObject>

    @POST("vendor/changeUserAccountStatus")
    suspend fun changeUserAccountStatusApi(
        @Header("token") token: String?, @Body data: JsonObject
    ): Response<JsonObject>
    @PUT("vendor/markAccountAsDefault/{id}")
    suspend fun makeDefaultAccount(
        @Header("token") token: String?, @Path("id") id: String
    ): Response<JsonObject>


    @PUT("vendor/deleteAccount/{id}")
    suspend fun deleteAccount(
        @Header("token") token: String?, @Path("id") id: String
    ): Response<JsonObject>

    @POST("vendor/addAccount")
    suspend fun getAddBankAccountRequest(@Header("token") token: String?,@Body jsonObject: JsonObject): Response<JsonObject>

    @GET("vendor/getNotifications")
    suspend fun getNotifications(
        @Header("token") token: String?,
        @Query("pageNumber") pageNumber: String?,
        @Query("limit") limit: String?,
    ): Response<NotificationListResponse>

    @POST("user/resendOtp")
    suspend fun resendOtpApi(@Body loginRequest: JsonObject): Response<JsonObject>

    @GET("saharaGo/getStates")
    suspend fun stateList(): Response<NewStateListResponse>

    @GET("saharaGo/getCities")
    suspend fun cityList(@Query("state") statName: String?): Response<NewCityListResponse>

    @POST("saharaGo/validateAddress")
    suspend fun validateAddress(@Body loginRequest: JsonObject): Response<JsonObject>

}