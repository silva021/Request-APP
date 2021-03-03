package com.silva021.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silva021.myapplication.DAO.AppDatabase
import com.silva021.myapplication.R
import com.silva021.myapplication.adapter.HistoricAdapter
import com.silva021.myapplication.databinding.ActivityHistoricBinding
import com.silva021.myapplication.model.Historic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoricActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityHistoricBinding
    lateinit var mAdapter: HistoricAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHistoricBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        CoroutineScope(Dispatchers.IO).launch {
            val allHistoric =
                AppDatabase.getInstanceDataBase(applicationContext).historyDao().getAllHistoric()
            withContext(Dispatchers.Main) {
                initRecycler(allHistoric)
            }
        }
    }

    fun initRecycler(list: List<Historic>) {
        mAdapter = HistoricAdapter(list)
        mBinding.recycler.adapter = mAdapter
        val layoutManager =
            LinearLayoutManager(applicationContext)
        mBinding.recycler.layoutManager = layoutManager
    }
}