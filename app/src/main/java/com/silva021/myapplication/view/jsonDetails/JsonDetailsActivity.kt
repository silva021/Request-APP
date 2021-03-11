package com.silva021.myapplication.view.jsonDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.silva021.myapplication.R
import com.silva021.myapplication.databinding.ActivityJsonDetaisBinding

class JsonDetailsActivity : AppCompatActivity(), JsonDetailsContract.View {
    lateinit var mBinding: ActivityJsonDetaisBinding
    lateinit var mPresenter: JsonDetailsPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityJsonDetaisBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar2)
        supportActionBar?.title = "Detalhe"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mPresenter = JsonDetailsPresenter(this)
        val url = intent.extras?.getString("url")

        if (url != null) {
            mPresenter.request(url)
        }
    }

    override fun showJson(json: String) {
        mBinding.txtJson.text = json
    }

    override fun showProgress(visibility: Boolean) {
        mBinding.progressCircular.visibility = if (visibility) View.VISIBLE else View.GONE
        mBinding.txtJson.visibility = if (visibility) View.GONE else View.VISIBLE
    }
}