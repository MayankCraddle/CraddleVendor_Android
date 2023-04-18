package com.cradlevendor.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterCancelOrderBinding
import com.cradlevendor.databinding.AdapterVendorReceivedOrderBinding
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.MyConstants
import com.cradlevendor.utils.localTimeFormet
import com.cradlevendor.utils.parseDateToddMMyyyy
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.*

class CancelOrderAdapter (
    private val context: Context,
    private val orderList: List<OrderListItem?>?
) :
    RecyclerView.Adapter<CancelOrderAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterCancelOrderBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_cancel_order, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = orderList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterCancelOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orderList: OrderListItem?, position: Int){
            itemView.tv_order_id.text="Order id: "+orderList!!.orderId

            if(orderList.cartMetaData!!.items!!.isNotEmpty()){
                itemView.tv_order_by_name.text="Order by "+ orderList.addressMetaData!!.firstName
                itemView.tv_order_dis.text= orderList.cartMetaData.items!!.get(0)!!.metaData!!.description
                val data= orderList.cartMetaData.items.get(0)!!


                itemView.tv_order_amount.text=data.currency+" "+data.totalPrice+" | "+data.quantity+"Item"

                val file = orderList.cartMetaData.items.get(0)!!.metaData!!.images
                if(file!!.size>0){
                    val imagePath = file[0]
                    Glide.with(context)
                        .load(MyConstants.file_Base_URL+imagePath).placeholder(R.drawable.loading)
                        .into(mBinding.ivReceivedOrder)

                }
            }

            val string= parseDateToddMMyyyy(orderList.createdOn)
            itemView.tv_create_on.text= localTimeFormet(string!!)

        }
    }
    override fun getItemCount(): Int {
        return orderList!!.size
    }


}