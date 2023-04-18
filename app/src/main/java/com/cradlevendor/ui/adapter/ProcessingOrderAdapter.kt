package com.cradlevendor.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterProcessingOrderBinding
import com.cradlevendor.ui.activity.OrderDetailActivity
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.localTimeFormet
import com.cradlevendor.utils.parseDateToddMMyyyy
import com.cradlevendor.utils.MyConstants
import kotlinx.android.synthetic.main.adapter_processing_order.view.*
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.*
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.tv_create_on
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.tv_order_amount
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.tv_order_by_name

import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.tv_order_id

class ProcessingOrderAdapter(
    private val context: Context,
    private val orderList: List<OrderListItem?>?, var changeState: ChangeState
) :
    RecyclerView.Adapter<ProcessingOrderAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterProcessingOrderBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_processing_order, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = orderList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterProcessingOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orderList: OrderListItem?, position: Int){
            Log.e("orderList",orderList.toString())
            itemView.tv_order_id.text=orderList!!.orderId

            itemView.tv_order_state_name.text= orderList.orderState
            itemView.tv_order_by_name.text="Order by "+ orderList.addressMetaData!!.firstName

            val string= parseDateToddMMyyyy(orderList.createdOn)
            itemView.tv_create_on.text= localTimeFormet(string!!)

            try {
                if(orderList.cartMetaData!!.items!!.isNotEmpty()){
                    mBinding.tvOrderDis.text= orderList.cartMetaData!!.items!!.get(0)!!.metaData!!.description
                    val data= orderList.cartMetaData.items!!.get(0)!!
                    itemView.tv_order_amount.text=data.currency+" "+data.totalPrice+" | "+data.quantity+" Item"
                    val file = orderList.cartMetaData.items!!.get(0)!!.metaData!!.images
                    if(file!!.size>0){
                        val imagePath = file[0]
                        Glide.with(context)
                            .load(MyConstants.file_Base_URL+imagePath).error(R.drawable.loading)
                            .into(mBinding.ivReceivedOrder)

                    }
                }
            }catch (e:Exception){

            }


            mBinding.ivMore.setOnClickListener {

                changeState.chatStateAction(orderList,"Payment_Completed")
            }
            mBinding.rlOrderDetails.setOnClickListener {
                val i = Intent(context, OrderDetailActivity::class.java)
                i.putExtra("data",orderList)
              context.startActivity(i)
            }
        }
    }
    override fun getItemCount(): Int {
        return orderList!!.size
    }
    interface ChangeState{
        fun chatStateAction(data: OrderListItem, state: String)
    }

}