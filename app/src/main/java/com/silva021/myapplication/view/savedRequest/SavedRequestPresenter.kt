package com.silva021.myapplication.view.savedRequest

import android.content.Context
import com.silva021.myapplication.DAO.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedRequestPresenter(
    private val mView: SavedRequestContract.View,
    private val mContext: Context
) : SavedRequestContract.Presenter {
    override fun getListHistoricRoom() {

        CoroutineScope(Dispatchers.IO).launch {
            var allRequestSaved =
                AppDatabase.getInstanceDataBase(mContext).requestSavedDAO().getAllRequestSaved()
            withContext(Dispatchers.Main) {
                mView.initRecycler(allRequestSaved)
            }
        }
    }

    override fun start() {
        getListHistoricRoom()
    }
}