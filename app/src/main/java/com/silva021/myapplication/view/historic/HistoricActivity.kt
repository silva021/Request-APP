package com.silva021.myapplication.view.historic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.silva021.myapplication.adapter.HistoricAdapter
import com.silva021.myapplication.adapter.SwipeRequestCallback
import com.silva021.myapplication.databinding.ActivityHistoricBinding
import com.silva021.myapplication.model.Historic
import com.silva021.myapplication.utils.Utils
import com.silva021.myapplication.view.jsonDetails.JsonDetailsActivity
import com.silva021.myapplication.view.jsonDetails.JsonDetailsPresenter
import com.silva021.myapplication.view.main.MainActivity

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

        val swipeRequestCallback = object : SwipeRequestCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                list[viewHolder.adapterPosition].url?.let { callMainActivity(it) }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeRequestCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.recycler)
    }

    fun callMainActivity(url: String) {
        startActivityForResult(
            Intent(this, JsonDetailsActivity::class.java).putExtra("url", url),
            Utils.REQUEST_CODE_HISTORIC
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Utils.REQUEST_CODE_HISTORIC -> mAdapter.notifyDataSetChanged()
            else -> {

            }
        }
    }
}