package com.cradlevendor.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterNotificationBinding
import com.cradlevendor.ui.model.NotificationListItem

class NotificationAdapter (private val context: Context, private var notificationList: List<NotificationListItem?>

) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterNotificationBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_notification, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = notificationList[position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterNotificationBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("NewApi")
        fun bind(stateList: NotificationListItem?, position: Int){
            mBinding.executePendingBindings()

            mBinding.tvNoticationTitle.text=stateList!!.title.toString()
            //  mBinding.tvCountryName.text=wishList!!.countryName
            mBinding.tvNotificationDis.text=stateList.body
            mBinding.tvNotificationTime.text=stateList.date




        }
    }
    override fun getItemCount(): Int {
        return notificationList.size
    }


}