package com.silva021.myapplication.view.jsonDetails

interface JsonDetailsContract {
    interface View {
        fun showJson(json : String)
        fun showProgress(visibility : Boolean)
    }
    interface Presenter {
        fun request(url : String)
    }
}