package com.silva021.myapplication.view.main

import android.view.View

interface MainContract {
    interface View {
        fun showSnackBar(text: String)
        fun showProgress(visibility : Boolean)
        fun updateTextView(response: okhttp3.Response, color : Int)
        fun responseSuccess(json : String)
        fun hideKeyboard()
    }

    interface Presenter {
        fun newRequest(URL : String)
        fun requestOnFailure(URL : String)
        fun insertRequestHistoric(response: okhttp3.Response)
        fun responseRequest(body: Any?, response: okhttp3.Response)
    }
}