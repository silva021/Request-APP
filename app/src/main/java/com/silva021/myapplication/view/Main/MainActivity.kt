package com.silva021.myapplication.view.Main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.silva021.myapplication.R
import com.silva021.myapplication.view.AboutActivity
import com.silva021.myapplication.view.Historic.HistoricActivity
import com.silva021.myapplication.databinding.ActivityMainBinding as ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mPresenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mPresenter = MainPresenter(this, applicationContext)

        mBinding.btnRequest.setOnClickListener {
            mPresenter.newRequest(mBinding.edtBaseUrl.text.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_historic -> {
                startActivity(Intent(this, HistoricActivity::class.java))
                true
            }
            R.id.menu_item_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showSnackBar(text: String) {
        Snackbar.make(mBinding.root, text, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
    }

    override fun showProgress(visibility: Boolean) {
        mBinding.progressCircular.visibility = if (visibility) View.VISIBLE else View.GONE
        mBinding.txtJson.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    override fun responseSuccess(json: String) {
        mBinding.txtJson.text = json
    }

}
