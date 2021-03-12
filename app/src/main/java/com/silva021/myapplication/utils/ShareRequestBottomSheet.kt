package com.silva021.myapplication.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.silva021.myapplication.databinding.LayoutBottomSheetsShareRequestBinding
import com.silva021.myapplication.listener.DialogSavedRequestLinester

class ShareRequestBottomSheet(private val json: String, private val url: String) :
    BottomSheetDialogFragment(), DialogSavedRequestLinester {
    lateinit var mBinding: LayoutBottomSheetsShareRequestBinding

    companion object {
        const val TAG = "ShareRequestBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = LayoutBottomSheetsShareRequestBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.btnShareJson.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, json)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        mBinding.btnSaveRequest.setOnClickListener {
            createDialogSaveRequest()
        }

    }

    private fun createDialogSaveRequest() {
        val mDialog = DialogSavedRequest(url, this)
        activity?.supportFragmentManager?.let { mDialog.show(it, "dialogSaveRequest") }
    }

    override fun requestSavedSucess() {
        dismiss()
    }

}