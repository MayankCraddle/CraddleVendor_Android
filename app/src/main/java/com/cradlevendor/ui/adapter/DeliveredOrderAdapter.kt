package com.cradlevendor.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterDeliveredOrderBinding
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.MyConstants
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.*

class DeliveredOrderAdapter (
    private val context: Context,
    private val orderList: List<OrderListItem?>?, var changeState: ChangeState
) :
    RecyclerView.Adapter<DeliveredOrderAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterDeliveredOrderBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_delivered_order, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = orderList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterDeliveredOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(orderList: OrderListItem?, position: Int){
            itemView.tv_order_id.text=orderList!!.orderId
            itemView.tv_create_on.text=orderList!!.createdOn
            itemView.tv_order_by_name.text="Order by "+orderList!!.addressMetaData!!.firstName
            itemView.tv_order_dis.text=orderList!!.cartMetaData!!.items!!.get(0)!!.metaData!!.description
            val data=orderList!!.cartMetaData!!.items!!.get(0)!!


            itemView.tv_order_amount.text=data.currency+" "+data.totalPrice+" "+data.quantity+"Item"
            /*   itemView.tv_order_dis.text=orderList!!.cartMetaData!!.items!!.get(0)!!.currency.toString()+" "+data!!.cartMetaData!!.items!!.get(0)!!.totalPrice.toString()
               itemView.tvCustomerName.text=data!!.addressMetaData!!.firstName
               itemView.tvProductQty.text=data!!.cartMetaData!!.items!!.get(0)!!.quantity.toString()
          */



            var file = orderList!!.cartMetaData!!.items!!.get(0)!!.metaData!!.images
            if(file!!.size>0){
                val imagePath = file[0]
                Glide.with(context)
                    .load(MyConstants.file_Base_URL+imagePath).error(R.drawable.loading)
                    .into(mBinding.ivReceivedOrder)

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