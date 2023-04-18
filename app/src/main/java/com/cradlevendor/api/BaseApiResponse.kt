package com.cradlevendor.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.cradlevendor.ui.activity.VendorLoginActivty
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.repository.ExceptionHandler

import org.json.JSONObject

abstract class BaseApiResponse(private  val context: Context) {
    suspend fun <T> safeApiCall(apiCall: suspend() -> retrofit2.Response<T>): ExceptionHandler<T>{
        try {
            val response = apiCall()
            if (response.isSuccessful){
                if (response.code() in 200..209){
                    val body = response.body()
                    body?.let {
                        return ExceptionHandler.Success(it)
                    }
                }
            }
             if(response.code()==401){
                val mpref=  SharaGoPref.getInstance(context)
                mpref.setLoginToken("")
                mpref.clear()
                val i = Intent(context, VendorLoginActivty::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(i)
            }
            val jObjError = JSONObject(response.errorBody()!!.string())
            val message=jObjError.optString("message")
            Log.e("Error",message)
        //    val result = message
            val result = message
            return error2(result)
        }catch (e:Exception){
            return ExceptionHandler.Error(e.message?:e.toString())
        }
    }
    private fun <T> error2(s: String): ExceptionHandler<T>  = ExceptionHandler.Error(s.toString())
}