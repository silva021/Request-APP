package com.silva021.myapplication.view.savedRequest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.silva021.myapplication.adapter.SavedRequestAdapter
import com.silva021.myapplication.adapter.SwipeRequestCallback
import com.silva021.myapplication.databinding.ActivitySavedRequestBinding
import com.silva021.myapplication.model.RequestSaved
import com.silva021.myapplication.utils.Utils
import com.silva021.myapplication.view.jsonDetails.JsonDetailsActivity

class SavedRequestActivity : AppCompatActivity(), SavedRequestContract.View {
    lateinit var mBinding: ActivitySavedRequestBinding
    lateinit var mAdapter: SavedRequestAdapter
    lateinit var mSavedRequestPresenter: SavedRequestPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySavedRequestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSavedRequestPresenter = SavedRequestPresenter(this, applicationContext)
        mSavedRequestPresenter.start()
    }

    override fun initRecycler(list: List<RequestSaved>) {
        mAdapter = SavedRequestAdapter(list, applicationContext)
        mBinding.recycler.adapter = mAdapter

        val swipeRequestCallback = object : SwipeRequestCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                list.get(viewHolder.adapterPosition).url.let { callMainActivity(it) }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeRequestCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.recycler)
    }

    fun callMainActivity(url: String) {
        startActivityForResult(
            Intent(this, JsonDetailsActivity::class.java).putExtra("url", url),
            Utils.REQUEST_CODE_SAVED_REQUEST
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Utils.REQUEST_CODE_SAVED_REQUEST -> mAdapter.notifyDataSetChanged()
            else -> {

            }
        }
    }
}