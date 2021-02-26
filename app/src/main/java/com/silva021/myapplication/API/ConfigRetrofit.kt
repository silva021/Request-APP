package com.silva021.myapplication.API

import com.silva021.myapplication.service.LocationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigRetrofit {

    private val mRetrofitInstance = Retrofit.Builder()
            .baseUrl("https://servicodados.ibge.gov.br/api/v1/localidades/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun LocationService() = mRetrofitInstance.create(LocationService::class.java)
}