package com.cradlevendor.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cradlevendor.R
import com.cradlevendor.chat.model.ChatActivity
import com.cradlevendor.chat.model.UserModelList
import com.cradlevendor.databinding.AdapterUserListBinding

class UserListAdapter(
    private val context: Context,
    private val orderList: List<String?>?,
   private val  userListModel: MutableList<UserModelList?>,
    /* private val contentJsonArray: List<ContentListItem?>?,*/
) :
    RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val  movieListBinding  = DataBindingUtil.inflate<AdapterUserListBinding>( LayoutInflater.from(parent.context),
            R.layout.adapter_user_list, parent, false)
        return MyViewHolder(movieListBinding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = userListModel[position]
        holder.bind(position,list!!)
    }
    inner class MyViewHolder(private val mBinding: AdapterUserListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(id: Int?, userModelList: UserModelList){
            mBinding.tvUserName.text=userModelList.userName
            mBinding.tvLastMsg.text=userModelList.lastMessage

               mBinding.rlOpenChat.setOnClickListener {
                   context.startActivity(
                    Intent(context, ChatActivity::class.java).putExtra("id",userModelList.enId).putExtra("userName",userModelList.userName))

            }
        }
    }
    override fun getItemCount(): Int {
        return userListModel.size
    }
    interface OpenDialogNow{
        fun bookSession(position: Int/*,data: CountryColorCodeListItem*/)
    }

}