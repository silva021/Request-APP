package com.silva021.myapplication.view.historic

import android.content.Context
import com.silva021.myapplication.DAO.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoricPresenter(private val mView: HistoricContract.View, private val mContext: Context) : HistoricContract.Presenter {
    override fun getListHistoricRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            val allHistoric =
                AppDatabase.getInstanceDataBase(mContext).historyDao().getAllHistoric()
            withContext(Dispatchers.Main) {
                mView.initRecycler(allHistoric)
            }
        }
    }

    override fun start() {
        getListHistoricRoom()
    }
}