package com.silva021.myapplication.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.silva021.myapplication.DAO.AppDatabase
import com.silva021.myapplication.R
import com.silva021.myapplication.listener.DialogSavedRequestLinester
import com.silva021.myapplication.model.RequestSaved
import com.silva021.myapplication.view.main.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogSavedRequest(private val url: String, private val mLinister: DialogSavedRequestLinester) :
    DialogFragment() {
    var edtName: EditText? = null
    var btnSave: Button? = null
    var textInputLayout: TextInputLayout? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mView: View? = activity?.layoutInflater?.inflate(R.layout.dialog_save_request, null)
        val alert = AlertDialog.Builder(activity)

        this.edtName = mView?.findViewById(R.id.edtName)
        this.btnSave = mView?.findViewById(R.id.btnSave)
        this.textInputLayout = mView?.findViewById(R.id.textInputLayout)

        alert.setView(mView)

        btnSave?.setOnClickListener {
            if (edtName?.text.toString().isEmpty()) {
                showError(true)
            } else {
                showError(false)
                insertRequest(RequestSaved(0, edtName?.text.toString(), url))
            }
        }
        return alert.create()
    }

    private fun showError(erroEnable: Boolean) {
        this.textInputLayout?.error = "Insira um nome primeiro"
        this.textInputLayout?.isErrorEnabled = erroEnable
    }

    private fun insertRequest(request: RequestSaved) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstanceDataBase(activity!!).requestSavedDAO().insertRequest(request)
            withContext(Dispatchers.Main) {
                dismiss()
                mLinister.requestSavedSucess()
            }
        }
    }
}