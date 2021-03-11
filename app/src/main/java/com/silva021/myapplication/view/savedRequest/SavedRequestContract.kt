package com.silva021.myapplication.view.savedRequest

import com.silva021.myapplication.model.RequestSaved

interface SavedRequestContract {
    interface View {
        fun initRecycler(list: List<RequestSaved>)
    }

    interface Presenter {
        fun getListHistoricRoom()
        fun start()
    }
}