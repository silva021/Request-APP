package com.silva021.myapplication.view.historic

import com.silva021.myapplication.model.Historic

interface HistoricContract {
    interface View {
        fun initRecycler(list: List<Historic>)
    }

    interface Presenter {
        fun getListHistoricRoom()
        fun start()
    }
}