package com.silva021.myapplication.view.jsonDetails

import android.content.Context
import com.google.gson.Gson
import com.silva021.myapplication.API.ConfigRetrofit
import com.silva021.myapplication.utils.Utils
import com.silva021.myapplication.view.main.MainContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsonDetailsPresenter(private val mView: JsonDetailsContract.View) : JsonDetailsContract.Presenter {
    override fun request(url: String) {
        try {
            val call = ConfigRetrofit(url).requestService().getListObject("")
            call.enqueue(object : Callback<List<Any>?> {
                override fun onResponse(call: Call<List<Any>?>, response: Response<List<Any>?>) {
                    mView.showJson(Utils.formatJson(Gson().toJson(response.body())))
                }

                override fun onFailure(call: Call<List<Any>?>, t: Throwable) {
                    requestOnFailure(url)
                }

            })
        } catch (e: Exception) {

        }
    }

    private fun requestOnFailure(URL: String) {
        ConfigRetrofit(URL).requestService()
            .getObject("")
            .enqueue(
                object : Callback<Any?> {
                    override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                        mView.showJson(Utils.formatJson(Gson().toJson(response.body())))
                    }

                    override fun onFailure(call: Call<Any?>, t: Throwable) {
                    }
                }
            )
    }
}