package com.silva021.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silva021.myapplication.databinding.LayoutItemSavedRequestBinding
import com.silva021.myapplication.model.RequestSaved

class SavedRequestAdapter(private val mList: List<RequestSaved>, private val mContext: Context) :
    RecyclerView.Adapter<SavedRequestAdapter.ViewHolderSavedRequest>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSavedRequest {
        val mBinding =
            LayoutItemSavedRequestBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderSavedRequest(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderSavedRequest, position: Int) {
        holder.mBinding.txtName.text = mList.get(position).name
        holder.mBinding.txtURL.text = mList.get(position).url
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolderSavedRequest(val mBinding: LayoutItemSavedRequestBinding) :
        RecyclerView.ViewHolder(mBinding.root)
}
