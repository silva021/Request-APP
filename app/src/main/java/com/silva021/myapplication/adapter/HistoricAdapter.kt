package com.silva021.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silva021.myapplication.databinding.LayoutItemHistoricBinding
import com.silva021.myapplication.model.Historic

class HistoricAdapter(private val mList: List<Historic>) :
    RecyclerView.Adapter<HistoricAdapter.ViewHolderHistoric>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistoric {
        val mBinding =
            LayoutItemHistoricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderHistoric(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderHistoric, position: Int) {
        holder.mBinding.txtCode.text = mList[position].code.toString()
        holder.mBinding.txtURL.text = mList[position].url
        holder.mBinding.txtMethod.text = mList[position].typeRequest
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolderHistoric(val mBinding: LayoutItemHistoricBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}