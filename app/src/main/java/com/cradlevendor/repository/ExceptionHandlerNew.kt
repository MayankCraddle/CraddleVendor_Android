package com.cradlevendor.repository

sealed class ExceptionHandlerNew<T>(val data:T?=null, val errorMessage: String?=null) {
    class Loading<T>:ExceptionHandlerNew<T>()
    class Success<T>(data: T?=null):ExceptionHandlerNew<T>(data=data)
    class Error<T>( errorMessage:String):ExceptionHandlerNew<T>(errorMessage=errorMessage)
}