package com.silva021.myapplication.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.silva021.myapplication.R
import com.silva021.myapplication.listener.DialogSavedRequestLinester
import com.silva021.myapplication.utils.DialogSavedRequest
import com.silva021.myapplication.view.AboutActivity
import com.silva021.myapplication.view.historic.HistoricActivity
import com.silva021.myapplication.view.savedRequest.SavedRequestActivity
import com.silva021.myapplication.databinding.ActivityMainBinding as ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View, DialogSavedRequestLinester {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mPresenter: MainPresenter
    private lateinit var mMenu: Menu
    private lateinit var mURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        mPresenter = MainPresenter(this, applicationContext)

        mBinding.btnRequest.setOnClickListener {
            cleanFields()
            mPresenter.newRequest(mBinding.edtBaseUrl.text.toString())
        }

    }

    fun cleanFields() {
        mBinding.txtJson.text = ""
        mBinding.txtMethod?.text = ""
        mBinding.txtCode?.text = ""
        mURL = mBinding.edtBaseUrl.text.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            mMenu = menu
        }
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        hideKeyboard()
        super.onStart()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_historic -> {
                startActivity(Intent(this, HistoricActivity::class.java))
                true
            }
            R.id.menu_item_share -> {
                createDialogSaveRequest()
                true
            }
            R.id.menu_item_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.menu_item_saved -> {
                startActivity(Intent(this, SavedRequestActivity::class.java))
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
        mMenu.findItem(R.id.menu_item_share).setVisible(true)

    }

    private fun createDialogSaveRequest() {
        val mDialog = DialogSavedRequest(mURL, this)
        mDialog.show(supportFragmentManager, "dialogSaveRequest")
    }

    override fun hideKeyboard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.edtBaseUrl.windowToken, 0)
    }

    override fun updateTextView(response: okhttp3.Response, color: Int) {
        mBinding.txtMethod?.text = response.request().method()
        mBinding.txtCode?.text = response.code().toString()
        mBinding.txtCode?.setTextColor(
            ContextCompat.getColor(
                mBinding.root.context,
                color
            )
        )
    }

    override fun requestSavedSucess() {
        hideKeyboard()
    }

}
