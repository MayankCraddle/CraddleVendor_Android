package com.cradlevendor.ui.activity

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.NotificationActivityBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.adapter.BanklistAdapter
import com.cradlevendor.ui.adapter.NotificationAdapter
import com.cradlevendor.ui.model.NotificationListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_account_details.*

class NotificationActivity : BaseActivity() {
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: NotificationActivityBinding
    private var dialog: Dialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = NotificationActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)

        initUI()
    }

    private fun initUI() {
        ivBack.setOnClickListener { finish() }
        apiHitWithResult()
    }

    private fun apiHitWithResult() {
        mViewModel.notificationListParam(SharaGoPref.getInstance(this).getLoginToken("").toString(),"1","10")
        mViewModel.lNotificationList.observe(this){
            when(it){
                is ExceptionHandler.Loading->{
                }
                is ExceptionHandler.Success->{

                    it.data?.let {
                        try {
                            if (it.notificationList!!.size>0){
                                mBinding.llNoDataFound.visibility=View.GONE
                                mBinding.recylerNotificationList.visibility= View.VISIBLE
                                addRecyclerView(it.notificationList)
                           }else{
                                mBinding.llNoDataFound.visibility=View.VISIBLE
                                mBinding.recylerNotificationList.visibility= View.GONE

                            }


                        }catch (e:Exception){
                          /*  mBinding.llNoDataFound.visibility= View.GONE
                            mBinding.recylerNotificationList.visibility= View.VISIBLE
*/

                        }

                    }
                }
                is ExceptionHandler.Error->{
               //     Utility.toastMessage(requireActivity(),it.errorMessage)
                }
            }
        }
    }

    private fun addRecyclerView(notificationList: List<NotificationListItem?>) {
        mBinding.mNotificationAdapter= NotificationAdapter(this,notificationList)

    }
}
