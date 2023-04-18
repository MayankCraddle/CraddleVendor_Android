package com.cradlevendor.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cradlevendor.R
import com.cradlevendor.databinding.AdapterBankListBinding
import com.cradlevendor.databinding.AdapterVendorReceivedOrderBinding
import com.cradlevendor.ui.model.AccountDetailListItem
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.localTimeFormet
import com.cradlevendor.utils.parseDateToddMMyyyy
import com.cradlevendor.utils.MyConstants
import kotlinx.android.synthetic.main.adapter_bank_list.view.*
import kotlinx.android.synthetic.main.adapter_vendor_received_order.view.*


class BanklistAdapter(
    private val context: Context,
    private val accountDetailList: List<AccountDetailListItem?>?,
    val bankListInterface: BankListInterface
) :
    RecyclerView.Adapter<BanklistAdapter.MyViewHolder>() {

    interface BankListInterface{
        fun banklistPos(pos:Int,id: String)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val movieListBinding = DataBindingUtil.inflate<AdapterBankListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_bank_list, parent, false
        )
        return MyViewHolder(movieListBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = accountDetailList!![position]
        holder.bind(list, position)
    }

    inner class MyViewHolder(private val mBinding: AdapterBankListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(accountDetailListItem: AccountDetailListItem?, position: Int) {
            itemView.tvAccountName.text = "" + accountDetailListItem?.accountMetadata?.accountName
            itemView.tvAccountNumber.text =
                "A/C No. - " + accountDetailListItem?.accountMetadata?.accountNumber
            itemView.tvBankSortCode.text =
                "Bank Sort Code - " + accountDetailListItem?.accountMetadata?.bankSortCode
            if (accountDetailListItem!!.isDefault!!)
                itemView.ll_default.visibility = View.VISIBLE
            else
                itemView.ll_default.visibility = View.GONE

            itemView.more_button.setOnClickListener {
                bankListInterface.banklistPos(adapterPosition, accountDetailListItem?.id.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return accountDetailList!!.size
    }


}