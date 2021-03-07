package com.silva021.myapplication.view.Historic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.silva021.myapplication.DAO.AppDatabase
import com.silva021.myapplication.adapter.HistoricAdapter
import com.silva021.myapplication.databinding.ActivityHistoricBinding
import com.silva021.myapplication.model.Historic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoricActivity : AppCompatActivity(), HistoricContract.View {
    lateinit var mBinding: ActivityHistoricBinding
    lateinit var mAdapter: HistoricAdapter
    lateinit var mHistoricPresenter: HistoricPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHistoricBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mHistoricPresenter = HistoricPresenter(this, applicationContext)
        mHistoricPresenter.start()
    }

    override fun initRecycler(list: List<Historic>) {
        mAdapter = HistoricAdapter(list)
        mBinding.recycler.adapter = mAdapter
    }
}