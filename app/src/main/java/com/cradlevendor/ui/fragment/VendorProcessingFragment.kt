package com.cradlevendor.vendor.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.chat.model.FbUserListActivity
import com.cradlevendor.databinding.FragmentProcessingBinding
import com.cradlevendor.ui.adapter.ProcessingOrderAdapter
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.dialog_order_state_filter.*
import org.json.JSONException

class VendorProcessingFragment : Fragment(), View.OnClickListener ,ProcessingOrderAdapter.ChangeState{
    private lateinit var v: View
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: FragmentProcessingBinding
    private var orderId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val response = (requireActivity().applicationContext as ApplicationClass).repository
        mBinding = FragmentProcessingBinding.inflate(inflater)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        v = mBinding.root
      //  mBinding.setVariable(BR.onAccount, this)
        findId()
        statusbarColourChange()
        return v
    }

    private fun statusbarColourChange() {

    }

    private fun findId() {

        apiHit()
        apiResult()
        mBinding.ivChatList.setOnClickListener {
            startActivity(Intent(requireActivity(), FbUserListActivity::class.java).putExtra("id",SharaGoPref.getInstance(requireActivity()).getVideoID("")).putExtra("email",SharaGoPref.getInstance(requireActivity()).getEmailId("")))

        }
    }
    private fun apiHit() {
        mViewModel.processingOrderStateParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!)

    }

    private fun apiResult(){
        mViewModel.lProgressOrder.observe(requireActivity()){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{
                    if(it.data!!.orderList!!.isNotEmpty()){
                        mBinding.recyclerReceivedOrder.visibility=View.VISIBLE
                        mBinding.ivNoDataFound.visibility=View.GONE
                        mBinding.processingOrderAdapter= ProcessingOrderAdapter(requireActivity(),it.data.orderList,this)

                    }else{
                        mBinding.recyclerReceivedOrder.visibility=View.GONE
                        mBinding.ivNoDataFound.visibility=View.VISIBLE
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
                        mViewModel.processingOrderStateParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!)


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
    private var dialog: Dialog? = null
    private fun moreDialog(orderId:String) {
        dialog = Dialog(requireActivity())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_order_state_filter)
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))
        dialog!!.setCanceledOnTouchOutside(true)

      val   tv_new = dialog!!.findViewById(R.id.tv_new) as TextView
       val tv_dismiss = dialog!!.findViewById(R.id.tv_dismiss) as TextView
       val tv_confirm = dialog!!.findViewById(R.id.tv_confirm) as TextView
       val tvpacked = dialog!!.findViewById(R.id.tvpacked) as TextView
      val  tv_shipped = dialog!!.findViewById(R.id.tv_shipped) as TextView
      val  tvCancelOrder = dialog!!.findViewById(R.id.tv_shipped) as TextView

      val  tv_remove_filter = dialog!!.findViewById(R.id.tv_remove_filter) as TextView


        /*  val tv_profile = dialog!!.findViewById(R.id.tv_profile) as TextView
          val tv_logout = dialog!!.findViewById(R.id.tv_logout) as TextView
          val tvChangePass = dialog!!.findViewById(R.id.tvChangePass) as TextView

        */
        //  tv_dismiss.setOnClickListener(this)

        dialog!!.cross.setOnClickListener{
            dialog!!.dismiss()
            // Toast.makeText(activity!!, "Internal server error", Toast.LENGTH_LONG).show()
        }
        tv_dismiss.setOnClickListener{
            dialog!!.dismiss()
            // Toast.makeText(activity!!, "Internal server error", Toast.LENGTH_LONG).show()
        }
     /*   tv_new.setOnClickListener{

            getFilteredCurrentOrder("Payment_Completed")

            dialog!!.dismiss()
            // Toast.makeText(activity!!, "Internal server error", Toast.LENGTH_LONG).show()
        }*/
        tv_confirm.setOnClickListener{
            getFilteredCurrentOrder("Confirmed")
            dialog!!.dismiss()
        }
        tvpacked.setOnClickListener{
            getFilteredCurrentOrder("Packed")
            dialog!!.dismiss()
        }
        tv_shipped.setOnClickListener{
            getFilteredCurrentOrder("Shipped")
            dialog!!.dismiss()

        }
        tv_remove_filter.setOnClickListener{
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    private fun getFilteredCurrentOrder(s: String) {

        val listDataChild = JsonObject()
        listDataChild.addProperty("state", s)
        mViewModel.changeOrderState(SharaGoPref.getInstance(requireActivity()).getLoginToken("")!!,orderId!!,listDataChild)

    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun chatStateAction(data: OrderListItem, state: String) {
        orderId=data.orderId
        moreDialog(data.orderId!!)
    }
}