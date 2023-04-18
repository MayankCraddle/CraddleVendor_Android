package com.cradlevendor.application

import android.app.Application
import com.cradlevendor.api.RetrofitHelper
import com.cradlevendor.repository.QuoteRepository


import com.cradlevendor.api.RetrofitService

class ApplicationClass : Application() {
    lateinit var repository: QuoteRepository
    //lateinit var quoteDataBase: QuoteDataBase
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(RetrofitService::class.java)
        repository= QuoteRepository(quoteService,applicationContext)

    }


}