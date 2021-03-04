package com.silva021.myapplication.API

import com.silva021.myapplication.service.RequestService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigRetrofit(baseUrl : String) {

    private val mRetrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun RequestService() = mRetrofitInstance.create(RequestService::class.java)
}