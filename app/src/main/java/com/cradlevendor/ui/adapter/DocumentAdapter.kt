package com.cradlevendor.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cradlevendor.R

import com.cradlevendor.databinding.AdapterDocumentBinding
import com.cradlevendor.ui.model.ListItem
import com.cradlevendor.ui.model.OrderListItem
import kotlinx.android.synthetic.main.adapter_document.view.*


class DocumentAdapter(
    private val context: Context,
    private val orderList: List<ListItem?>?, var clickItem: DocumentItem
) :
    RecyclerView.Adapter<DocumentAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterDocumentBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_document, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = orderList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterDocumentBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(orderList: ListItem?, position: Int){

            itemView.tvUploadDocument.text=orderList!!.name
            itemView.rl_one.setOnClickListener {
                clickItem.clickItem(orderList)
            }
        }
    }
    override fun getItemCount(): Int {
        return orderList!!.size
    }
    interface DocumentItem{
        fun clickItem(data: ListItem)
    }

}