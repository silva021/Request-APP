package com.silva021.myapplication.view.Main

import android.content.Context

interface MainContract {
    interface View {
        fun showSnackBar(text: String)
        fun showProgress(visibility : Boolean)
        fun responseSuccess(json : String)
    }

    interface Presenter {
        fun newRequest(URL : String)
        fun requestOnFailure(URL : String)
        fun insertRequestHistoric(response: okhttp3.Response)
        fun responseRequest(body: Any?, response: okhttp3.Response)
    }
}