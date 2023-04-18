package com.cradlevendor.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterStateListBinding
import com.cradlevendor.intarfaces.ItemClickListner
import com.cradlevendor.utils.MyConstants

class StateAdapter (private val context: Context, private var stateList: ArrayList<String?>?,
                    private val clickListener: ItemClickListner
) :
    RecyclerView.Adapter<StateAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterStateListBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_state_list, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = stateList!![position]
        holder.bind(list,position)
    }
    inner class MyViewHolder(private val mBinding: AdapterStateListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("NewApi")
        fun bind(stateList: String?, position: Int){
            mBinding.executePendingBindings()

            mBinding.ivCountryImage.visibility=android.view.View.GONE
            //  mBinding.tvCountryName.text=wishList!!.countryName
            mBinding.tvCountryName.setTextColor(context.getColor(R.color.black))
            mBinding.tvCountryName.text=stateList.toString()

            mBinding.releState.setOnClickListener {
                clickListener.onClickItem(position, MyConstants.STATE_REQ_CODE,stateList!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return stateList!!.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterdNames: ArrayList<String?>?): ArrayList<String?>? {
        this.stateList = filterdNames
        notifyDataSetChanged()
        return stateList
    }


}