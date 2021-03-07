package com.silva021.myapplication.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestService {

    @GET("{endPoint}")
    fun getListObject(@Path("endPoint") endPoint : String) : Call<List<Any>>

    @GET("{endPoint}")
    fun getObject(@Path("endPoint") endPoint : String) : Call<Any>
}