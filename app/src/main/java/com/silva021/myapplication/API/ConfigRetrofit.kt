package com.silva021.myapplication.API

import com.silva021.myapplication.service.LocationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigRetrofit(private val baseUrl : String) {

    private val mRetrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun LocationService() = mRetrofitInstance.create(LocationService::class.java)
}