package com.cradlevendor.vendor.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.chat.model.FbUserListActivity
import com.cradlevendor.databinding.FragmentDeliveredOrderBinding
import com.cradlevendor.ui.adapter.DeliveredOrderAdapter
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import org.json.JSONException

class VendorDeliveredFragment: Fragment(), View.OnClickListener,DeliveredOrderAdapter.ChangeState {
    private lateinit var v: View
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: FragmentDeliveredOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val response = (requireActivity().applicationContext as ApplicationClass).repository
        mBinding = FragmentDeliveredOrderBinding.inflate(inflater)
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
                        mBinding.recyclerReceivedOrder.visibility=View.GONE
                        mBinding.ivNoDataFound.visibility=View.VISIBLE
                    //    mBinding.deliveredOrderAdapter= DeliveredOrderAdapter(requireActivity(),it.data.orderList,this)

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
        TODO("Not yet implemented")
    }

    override fun chatStateAction(data: OrderListItem, state: String) {

    }
}