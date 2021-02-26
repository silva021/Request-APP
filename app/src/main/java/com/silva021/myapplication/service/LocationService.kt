package com.silva021.myapplication.service

import com.silva021.myapplication.model.Location
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {
    @GET("estados/")
    fun getAllUf() : Call<List<Location>>
}