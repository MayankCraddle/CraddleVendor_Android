package com.cradlevendor.chat.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cradlevendor.R
import com.cradlevendor.chat.model.Consersation
import com.cradlevendor.chat.model.conversations
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.getDateFormat
import com.cradlevendor.utils.getTimeFormat
import kotlinx.android.synthetic.main.rc_item_patient_chat_with_physician.view.*

class ChatAdapter (private val mContext: Context, private var consersation: Consersation?, private var queryId: String?) : RecyclerView.Adapter<ChatAdapter.MyHolderView>() {
    private val viewTypePhysicianMessage = 0
    private val viewTypePatientMessage: Int = 1
    private var healthPref: SharaGoPref? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyHolderView {
        healthPref = SharaGoPref.getInstance(mContext)


            if (viewType == viewTypePatientMessage) {
                val view = LayoutInflater.from(mContext).inflate(R.layout.rc_item_patient_chat_with_physician, viewGroup, false)
                return MyHolderView(view)
            } else if (viewType == viewTypePhysicianMessage) {
                val view = LayoutInflater.from(mContext).inflate(R.layout.rc_item_physician_chat_with_patient, viewGroup, false)
                return MyHolderView(view)
            }

        return null!!
    }

    override fun onBindViewHolder(myHolderView: MyHolderView, position: Int) {
        myHolderView.bind(consersation!!.listMessageData[position])
    }

    override fun getItemCount(): Int {
        return consersation!!.listMessageData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (consersation!!.listMessageData[position].fromID == healthPref?.getGoogleID(""))
            viewTypePatientMessage
        else
            viewTypePhysicianMessage

    }

    inner class MyHolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SimpleDateFormat")
        fun bind(data: conversations?) {
            data?.let {
                if (it.content != null) {
                    if (it.type == mContext.getString(R.string.chat_image) || it.type == mContext.getString(
                            R.string.chat_file
                        )) {
                        itemView.chat_file_layout.visibility = View.VISIBLE
                        itemView.chat_message_layout.visibility = View.GONE
                        val extension = it.content.substring(it.content.lastIndexOf("."))
                        itemView.time_tv.text = getTimeFormat(it)
                        itemView.date_tv.text = getDateFormat(it)
                    } else if (it.type == mContext.getString(R.string.chat_text)) {
                        itemView.chat_file_layout.visibility = View.GONE
                        itemView.chat_message_layout.visibility = View.VISIBLE
                        itemView.message_tv.text = it.content
                        itemView.time_tv.text = getTimeFormat(it)
                        itemView.date_tv.text = getDateFormat(it)
                    }
                } else {
                    itemView.chat_file_layout.visibility = View.GONE
                    itemView.chat_message_layout.visibility = View.GONE
                    itemView.data_and_time_layout.visibility = View.GONE
                }
            }

        }
    }
}