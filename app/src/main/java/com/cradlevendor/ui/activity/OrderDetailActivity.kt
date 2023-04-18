package com.cradlevendor.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityOrderDetailBinding
import com.cradlevendor.ui.model.OrderListItem
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.localTimeFormet
import com.cradlevendor.utils.parseDateToddMMyyyy
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.utils.MyConstants
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.json.JSONException

class OrderDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityOrderDetailBinding
    var data: OrderListItem?=null
    private var orderID: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        //  mBinding.forgotPassVM = mViewModel
        //  mBinding.setVariable(BR.onClickChangePass, this)
        statusBarColourChange()
        initUI()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun statusBarColourChange() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getColor(R.color.blue_light))
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        val data = if (Build.VERSION.SDK_INT >= 33) {
          intent.getParcelableExtra("data", OrderListItem::class.java)
        } else {
            intent.getParcelableExtra("data")
        }

        Log.e("orderItem",data.toString())



        try {
            val file= data!!.cartMetaData!!.items!!.get(0)!!.metaData!!.images
            if(file!!.isNotEmpty()){
                val imagePath = file[0]
                Glide.with(this)
                    .load(MyConstants.file_Base_URL+imagePath).placeholder(R.drawable.loading)
                    .into(mBinding.ivProductItem)

            }
            if (data.orderState!!.isNotEmpty()){
                mBinding.tvOderState.text= data.orderState
            }

            if (data.createdOn!!.isNotEmpty()){
                val string= parseDateToddMMyyyy(data.createdOn)
                mBinding.tvCreateOn.text= localTimeFormet(string!!)
            }
            if (data.orderId!!.isNotEmpty()){
                mBinding.tvOderID.text= "Order Id:"+ data.orderId
            }
            orderID=data.orderId
            val cartMetadata=data.cartMetaData!!.items!!.get(0)!!
            if (cartMetadata.name!!.isNotEmpty()){
                mBinding.tvProductName.text= cartMetadata.name
            }
            if (data.cartMetaData.items!!.get(0)!!.discountedPrice.toString().isNotEmpty()){
                mBinding.tvProductPriceWithQuantity.text= data.cartMetaData.items!!.get(0)!!.discountedPrice.toString()+"*"+"1"
            }
            if (data.cartMetaData.items.get(0)!!.totalPrice.toString().isNotEmpty()){
                mBinding.tvTotalPrice.text= data.cartMetaData.items.get(0)!!.totalPrice.toString()
            }
            if (data.cartMetaData.items.get(0)!!.totalPrice.toString().isNotEmpty()){
                mBinding.tvGrantTotal.text= getString(R.string.currency_symbol)+" "+data.cartMetaData.items!!.get(0)!!.totalPrice.toString()
            }
            if (data.cartMetaData.items.get(0)!!.quantity.toString().isNotEmpty()){
                mBinding.tvProductQty.text= data.cartMetaData.items.get(0)!!.quantity.toString()+" Item"
            }
            if (data.cartMetaData.items.get(0)!!.quantity.toString().isNotEmpty()){
                mBinding.tvItemTotal.text= getString(R.string.currency_symbol)+" "+data.cartMetaData.items.get(0)!!.quantity.toString()
            }
            if (data.addressMetaData!!.city!!.isNotEmpty()){
                mBinding.tvCity.text= data.addressMetaData!!.city
            }

            if (data.addressMetaData.zipcode!!.isNotEmpty()){
                mBinding.tvPinCode.text= data.addressMetaData.zipcode
            }
            if (intent.getStringExtra("address")!!.isNotEmpty()){
                mBinding .tvAddress.text=intent.getStringExtra("address")
            }
            if (intent.getStringExtra("image")!!.isNotEmpty()){
                val image = intent.getStringExtra("image")
                Glide.with(this).load(MyConstants.file_Base_URL+image).error(R.drawable.loading).into(iv_product_item)

            }





        }catch (_:Exception){

        }
        llAcceptOrder.setOnClickListener {

        }

        iv_back_order_details.setOnClickListener { finish() }
    }

    private fun acceptOrder(){
        val listDataChild = JsonObject()
        listDataChild.addProperty("state", "Confirmed")
        mViewModel.changeOrderState(SharaGoPref.getInstance(this).getLoginToken("")!!,orderID!!,listDataChild)

        mViewModel.lChangeOrderState.observe(this){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{

                    try {
                        Toast.makeText(this, "State Changed Successfully!", Toast.LENGTH_LONG).show()
                        mViewModel.processingOrderStateParam(SharaGoPref.getInstance(this).getLoginToken("")!!)
                        finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                is ExceptionHandler.Error->{

                    Utility.toastMessage(this,it.errorMessage)
                }
            }
        }
    }

    override fun onClick(p0: View?) {

    }
}
