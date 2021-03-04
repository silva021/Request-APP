package com.silva021.myapplication.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestService {

    @GET("{endPoint}")
    fun getAllListObject(@Path("endPoint") endPoint : String) : Call<List<Any>>

    @GET("{endPoint}")
    fun getAllObject(@Path("endPoint") endPoint : String) : Call<Any>
}