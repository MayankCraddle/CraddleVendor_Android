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
import com.cradlevendor.databinding.AdapterVendorReceivedOrderBinding
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.localTimeFormet
import com.cradlevendor.utils.parseDateToddMMyyyy
import com.cradlevendor.utils.MyConstants
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.*


class VendorReceivedOrderAdapter(
    private val context: Context,
   private val orderList: List<OrderListItem?>?,var changeState: ChangeState,var orderDetail:ChangeState,var cancelOrder:ChangeState
) :
    RecyclerView.Adapter<VendorReceivedOrderAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterVendorReceivedOrderBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_vendor_received_order, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = orderList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterVendorReceivedOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orderList: OrderListItem?, position: Int){
            itemView.tv_order_id.text="Order id: "+orderList!!.orderId

            if(orderList.cartMetaData!!.items!!.isNotEmpty()){

                itemView.tv_order_by_name.text="Order by "+ orderList.addressMetaData!!.firstName


                try {
                    itemView.tv_order_dis.text= orderList.cartMetaData.items!!.get(0)!!.metaData!!.description

                    val file = orderList.cartMetaData.items.get(0)!!.metaData!!.images
                    if(file!!.size>0){
                        val imagePath = file[0]
                        Glide.with(context)
                            .load(MyConstants.file_Base_URL+imagePath).placeholder(R.drawable.loading)
                            .into(mBinding.ivReceivedOrder)

                    }
                }catch (e:Exception){

                }
                val data= orderList.cartMetaData.items!!.get(0)!!


                itemView.tv_order_amount.text=data.currency+" "+data.totalPrice+" | "+data.quantity+"Item"


            }

            val string= parseDateToddMMyyyy(orderList.createdOn)
            itemView.tv_create_on.text= localTimeFormet(string!!)

            mBinding.ivAccept.setOnClickListener {

                changeState.chatStateAction(orderList,"Do you want to change the status of the order from new to confirmed")
           }
            mBinding.rlOrderDetails.setOnClickListener {
                orderDetail.orderDetails(orderList,position)
           }
            mBinding.ivCencelOrder.setOnClickListener {
                cancelOrder.cancelOrder(orderList)
           }
        }
    }
    override fun getItemCount(): Int {
        return orderList!!.size
    }
    interface ChangeState{
        fun chatStateAction(data: OrderListItem,state: String)
        fun orderDetails(data: OrderListItem,position: Int)
        fun cancelOrder(data: OrderListItem)
    }

}