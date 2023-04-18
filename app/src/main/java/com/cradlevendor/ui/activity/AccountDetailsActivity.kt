package com.cradlevendor.ui.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityAccountDetailsBinding
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.adapter.BanklistAdapter
import com.cradlevendor.ui.adapter.VendorReceivedOrderAdapter
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_account_details.*
import org.json.JSONException
import org.json.JSONObject

class AccountDetailsActivity : BaseActivity(), BanklistAdapter.BankListInterface {
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityAccountDetailsBinding
    private var dialog: Dialog? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        initUI()
    }

    private fun initUI() {

        ivBack.setOnClickListener { finish() }
        bankListApi()
        addBankAccount.setOnClickListener {
            startActivityForResult(Intent(this, AddBankAccountActivity::class.java), 100)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    private fun bankListApi() {
        mViewModel.accountDetailListParam(SharaGoPref.getInstance(this).getLoginToken("")!!)
        mViewModel.lAccountDetailList.observe(this) {
            when (it) {
                is ExceptionHandler.Loading -> {

                }
                is ExceptionHandler.Success -> {
                    if (it.data!!.accountDetailList!!.isNotEmpty()) {
                        // Log.e("orderlist", it.data.orderList.toString())
                        /*mBinding.recyclerReceivedOrder.visibility= View.VISIBLE
                        mBinding.llNoDataFound.visibility= View.GONE
                        mBinding.ivNoDataFound.visibility= View.GONE*/
                        mBinding.mBanklistAdapter =
                            BanklistAdapter(this, it.data.accountDetailList, this)

                    } else {
                        /*mBinding.recyclerReceivedOrder.visibility= View.GONE

                        mBinding.ivNoDataFound.visibility= View.VISIBLE
                        mBinding.llNoDataFound.visibility= View.VISIBLE*/
                    }
                }
                is ExceptionHandler.Error -> {

                    Utility.toastMessage(this, it.errorMessage)
                }
            }
        }

    }

    override fun banklistPos(pos: Int, id: String) {
        moreDialog(id)
    }

    private fun moreDialog(id: String) {

        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dailog_address_filter)
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation1;
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))

        dialog!!.setCanceledOnTouchOutside(true)

        val tv_mark_as_default = dialog!!.findViewById(R.id.tv_mark_as_default) as TextView
        val tv_edit_address = dialog!!.findViewById(R.id.tv_edit_address) as TextView
        val tv_delete_address = dialog!!.findViewById(R.id.tv_delete_address) as TextView
        val tv_dismiss = dialog!!.findViewById(R.id.tv_dismiss) as TextView
        val viewEdit = dialog!!.findViewById(R.id.viewEdit) as View
        val viewDelete = dialog!!.findViewById(R.id.viewDelete) as View
        val rl_dialog = dialog!!.findViewById(R.id.rl_dialog) as RelativeLayout
        tv_edit_address.visibility = View.GONE
        tv_delete_address.visibility = View.VISIBLE
        viewEdit.visibility = View.GONE
        viewDelete.visibility = View.VISIBLE
        tv_mark_as_default.setOnClickListener {
            makeDefaultApi(id)
            dialog!!.dismiss()
        }
        rl_dialog.setOnClickListener {

            dialog!!.dismiss()
        }
        /*tv_edit_address.setOnClickListener {
            startActivity(
                Intent(this, UserAddAddressActivity::class.java).apply {
                    putExtra("data", data).putExtra("id",addressListItem).putExtra("buttonName","Update address")
                }
            )
            dialog!!.dismiss()
        }
       */
        tv_delete_address.setOnClickListener {
            deleteAccountApi(id)
          //  mViewModel.userDeleteAddress(SharaGoPref.getInstance(this).getLoginToken("").toString(),addressListItem)
            dialog!!.dismiss()
        }
        tv_dismiss.setOnClickListener {
            dialog!!.dismiss()
        }

        dialog!!.show()
    }

    fun makeDefaultApi(id: String) {
        mViewModel.userMarkAsDefault(
            SharaGoPref.getInstance(this).getLoginToken("").toString(),
            id
        )
        mViewModel.luserMarkAsDefaultParam.observe(this) { it ->
            when (it) {
                is ExceptionHandler.Loading -> {
                    dismissLoader()
                }
                is ExceptionHandler.Success -> {
                    dismissLoader()
                    it.data?.let {
                        val jsonObject = JSONObject(it.toString())
                        Toast.makeText(this, jsonObject.optString("message"), Toast.LENGTH_LONG)
                            .show()
                        bankListApi()

                    }

                }
                is ExceptionHandler.Error -> {
                    dismissLoader()

                }
            }
        }
    }
    fun deleteAccountApi(id: String) {
        mViewModel.deleteAccount(
            SharaGoPref.getInstance(this).getLoginToken("").toString(),
            id
        )
        mViewModel.lDeleteAccountParam.observe(this) { it ->
            when (it) {
                is ExceptionHandler.Loading -> {
                    dismissLoader()
                }
                is ExceptionHandler.Success -> {
                    dismissLoader()
                    it.data?.let {
                        val jsonObject = JSONObject(it.toString())
                        Toast.makeText(this, jsonObject.optString("message"), Toast.LENGTH_LONG)
                            .show()
                        bankListApi()

                    }

                }
                is ExceptionHandler.Error -> {
                    dismissLoader()

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            bankListApi()
        }
    }
}