package com.cradlevendor.ui.fragment
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.chat.model.FbUserListActivity
import com.cradlevendor.databinding.FragmentVendorReceivedorderBinding
import com.cradlevendor.ui.activity.OrderDetailActivity
import com.cradlevendor.ui.adapter.VendorReceivedOrderAdapter
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility

import com.cradlevendor.repository.ExceptionHandler

import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import org.json.JSONException

class ReceivedOrderFragment : Fragment(), View.OnClickListener,VendorReceivedOrderAdapter.ChangeState {
    private lateinit var v: View
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: FragmentVendorReceivedorderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val response = (requireActivity().applicationContext as ApplicationClass).repository
        mBinding = FragmentVendorReceivedorderBinding.inflate(inflater)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        v = mBinding.root
    //    mBinding.setVariable(BR.onAccount, this)
        findId()

        return v
    }

    private fun findId() {

        apiHit()
        apiResult()
        mBinding.ivChatList.setOnClickListener {
            startActivity(Intent(requireActivity(), FbUserListActivity::class.java).putExtra("id",SharaGoPref.getInstance(requireActivity()).getVideoID("")).putExtra("email",SharaGoPref.getInstance(requireActivity()).getEmailId("")))

        }
    }

    private fun apiHit() {
        mViewModel.currentOrderStateParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!)

    }

    private fun apiResult(){
        mViewModel.lcurrentOrder.observe(requireActivity()){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{
                    if(it.data!!.orderList!!.isNotEmpty()){
                        Log.e("orderlist", it.data.orderList.toString())
                        mBinding.recyclerReceivedOrder.visibility=View.VISIBLE
                        mBinding.llNoDataFound.visibility=View.GONE
                        mBinding.ivNoDataFound.visibility=View.GONE
                        mBinding.mVendorReceivedOrderAdapter= VendorReceivedOrderAdapter(requireActivity(),it.data.orderList,this,this,this)

                    }else{
                        mBinding.recyclerReceivedOrder.visibility=View.GONE

                        mBinding.ivNoDataFound.visibility=View.VISIBLE
                        mBinding.llNoDataFound.visibility=View.VISIBLE
                    }
                }
                is ExceptionHandler.Error->{

                    Utility.toastMessage(requireActivity(),it.errorMessage)
                }
            }
        }
        mViewModel.lChangeOrderState.observe(requireActivity()){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{

                    try {
                        Toast.makeText(requireActivity(), "State Changed Successfully!", Toast.LENGTH_LONG).show()

                        mViewModel.currentOrderStateParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                is ExceptionHandler.Error->{

                    Utility.toastMessage(requireActivity(),it.errorMessage)
                }
            }
        }
    }



    override fun onClick(p0: View?) {

    }

    private fun cancelOrderApiResult(){
        mViewModel.lChangeOrderState.observe(requireActivity()){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{

                    try {
                        Toast.makeText(requireActivity(), "State Changed Successfully!", Toast.LENGTH_LONG).show()

                        mViewModel.currentOrderStateParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                is ExceptionHandler.Error->{

                    Utility.toastMessage(requireActivity(),it.errorMessage)
                }
            }
        }
    }

    private var dialog1: Dialog? = null
    private fun quesDialog(orderId: String, listDataChild: JsonObject, state: String) {
        dialog1 = Dialog(requireActivity())
        dialog1!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1!!.setContentView(R.layout.dailog_order_state_change)
        dialog1!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        dialog1!!.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.full_transparent)))
        dialog1!!.setCanceledOnTouchOutside(true)

        val tv_reject_reason = dialog1!!.findViewById(R.id.tv_reject_reason) as TextView
        val iv_cancel_dailog = dialog1!!.findViewById(R.id.iv_cancel_dailog) as ImageView
        val tv_Ok = dialog1!!.findViewById(R.id.tv_Ok) as TextView
        tv_reject_reason.text=state
      
        iv_cancel_dailog.setOnClickListener(this)
        tv_Ok.setOnClickListener(this)
        
        tv_Ok.setOnClickListener{
            dialog1!!.dismiss()
            changeOrderState(orderId,listDataChild)
        }
        iv_cancel_dailog.setOnClickListener{
            dialog1!!.dismiss()
        }
        dialog1!!.show()
    }

    override fun chatStateAction(data: OrderListItem, state: String) {

        val listDataChild = JsonObject()
        listDataChild.addProperty("state", "Confirmed")
        quesDialog(data.orderId!!,listDataChild,state)
    }

    override fun cancelOrder(data: OrderListItem) {
        val listDataChild = JsonObject()
        listDataChild.addProperty("state", "Cancelled")
        listDataChild.addProperty("comment", "")
        mViewModel.changeOrderState(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!,
            data.orderId!!,listDataChild)
        cancelOrderApiResult()
    }


    override fun orderDetails(data: OrderListItem, position: Int) {
        try {
            val i = Intent(requireActivity(), OrderDetailActivity::class.java)
            i.putExtra("data",data)
            i.putExtra("name",data.addressMetaData!!.firstName)
            i.putExtra("orderId",data.orderId)
            i.putExtra("createdOn",data.createdOn)

            i.putExtra("orderState",data.orderState)
            i.putExtra("address", data.addressMetaData.streetAddress)
            i.putExtra("phone_number", data.addressMetaData.phone)
            if(data.cartMetaData!!.items!!.isNotEmpty()){
                i.putExtra("totalPrice1", data.cartMetaData.items!!.get(0)!!.totalPrice.toString())
                i.putExtra("qty", data.cartMetaData.items.get(0)!!.quantity.toString())
                i.putExtra("price", data.cartMetaData.items.get(0)!!.price.toString())
                i.putExtra("image", data.cartMetaData.items.get(0)!!.metaData!!.images!!.get(0))
            }

            i.putExtra("status",data.orderState)
            i.putExtra("phone_number", data.addressMetaData.phone)

            startActivity(i)
        }catch (_:Exception){

        }

    }

    private fun changeOrderState(orderId: String, listDataChild: JsonObject) {
        mViewModel.changeOrderState(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!,orderId,listDataChild)
    }
}
