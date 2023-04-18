package com.cradlevendor.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cradlevendor.R
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.chat.model.FbUserListActivity
import com.cradlevendor.databinding.FragmentVendorProfileBinding
import com.cradlevendor.ui.common_fragment.LoaderFragment
import com.cradlevendor.ui.model.UserMetaData
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.utils.Utility
import com.cradlevendor.utils.showToast
import com.cradlevendor.repository.ExceptionHandler
import com.cradlevendor.ui.activity.*
import com.cradlevendor.utils.MyConstants
import com.cradlevendor.utils.MyHelper
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_vendor_profile.view.*
import kotlinx.android.synthetic.main.logout_dialog.*
import org.json.JSONException

class VendorProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: FragmentVendorProfileBinding
    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val response = (requireActivity().applicationContext as ApplicationClass).repository
        mBinding = FragmentVendorProfileBinding.inflate(inflater)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]
        v = mBinding.root
      //  mBinding.setVariable(BR.onAccount, this)
        findId()
        return v
    }
    private fun apiHit(){
        if(MyHelper.isNetworkConnected(requireActivity())){
           // LoaderFragment.showLoader(requireActivity().supportFragmentManager)

            Log.e("token", SharaGoPref.getInstance(requireActivity()).getLoginToken("").toString())
            mViewModel.detailByTokenParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("").toString())

        }else requireActivity().showToast(getString(R.string.no_internet_connection))
    }


    private var userMetaData: UserMetaData?=null
    private var emaild_id: String?=null
    @SuppressLint("SetTextI18n")

    private fun apiResult(){
        mViewModel.lDetailByToken.observe(requireActivity()){ it ->
            when(it){
                is ExceptionHandler.Loading->{
                    LoaderFragment.dismissLoader(requireActivity().supportFragmentManager)
                }
                is ExceptionHandler.Success->{
                    LoaderFragment.dismissLoader(requireActivity().supportFragmentManager)
                    it.data?.let {
                        Log.e("profile",it.toString())
                        emaild_id=it.emailMobile.toString()
                        /* if (it.metaData!!.image!=null){
                             Glide.with(requireActivity()).load(MyConstants.file_Base_URL_flag+it.metaData!!.image).into(mBinding.ivUserProfile)
                         }*/
                        try {
                            if (it.metaData!=null){
                                userMetaData= it.metaData
                                mBinding.tvVendorName.text= it.metaData.firstName+" "+it.metaData.lastName
                                mBinding.tvVendorEmail.text= it.metaData.country
                                Glide.with(requireActivity()).load(MyConstants.file_Base_URL+ it.metaData.image).error(
                                    R.drawable.avatar).into(mBinding.ivVendorProfile)
                            }

                        }catch (_:Exception){

                        }
                    }

                }
                is ExceptionHandler.Error->{
                    LoaderFragment.dismissLoader(requireActivity().supportFragmentManager)

                }
            }
        }
    }


    private fun findId() {

        mBinding.ivVendorProfile.setOnClickListener{
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java).putExtra("first_name",userMetaData!!.firstName.toString()).putExtra("last_name",userMetaData!!.lastName.toString())
                .putExtra("email_id",emaild_id))
        }
        apiHit()
        apiResult()
        v.rl_open_my_profile.setOnClickListener(this)
       v.rlAccount.setOnClickListener(this)
       v.rl_change_pass.setOnClickListener(this)
        v.rl_logout.setOnClickListener(this)
        v.rl_chat.setOnClickListener(this)
        v.llDeleteAccount.setOnClickListener(this)
        v.llDeactivateAccount.setOnClickListener(this)
        v.ivOpenNotificationList.setOnClickListener(this)


    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.rl_open_my_profile -> {
                startActivity(Intent(requireActivity(), EditProfileActivity::class.java).putExtra("first_name",userMetaData!!.firstName.toString()).putExtra("last_name",userMetaData!!.lastName.toString()).putExtra("email_id",emaild_id))

            }R.id.ivOpenNotificationList -> {
                startActivity(Intent(requireActivity(), NotificationActivity::class.java).putExtra("first_name",userMetaData!!.firstName.toString()).putExtra("last_name",userMetaData!!.lastName.toString()).putExtra("email_id",emaild_id))

            }R.id.rlAccount -> {
                startActivity(Intent(requireActivity(), AccountDetailsActivity::class.java))

            }R.id.rl_change_pass -> {
            startActivity(Intent(requireActivity(), ChangePasswordWithLoginActivity::class.java))

            }
            R.id.llDeleteAccount->{
                startActivity(Intent(requireActivity(), DeleteAccountActivity::class.java))
            }R.id.llDeactivateAccount->{
            startActivity(Intent(requireActivity(), DeActivateAccountActivity::class.java))
        }
            R.id.rl_chat -> {
            startActivity(Intent(requireActivity(), FbUserListActivity::class.java).putExtra("id","c5f63cf93f7655e3734f5a6abeb5f740").putExtra("email","titan@yopmail.com"))

            }R.id.rl_logout -> {
            logOutDialog()
            }
        }
    }
    private fun logOutDialog() {
        dialog = Dialog(requireActivity())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.logout_dialog)
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation1;
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(getResources().getColor(R.color.full_transparent)))
        dialog!!.rlYes.setOnClickListener {logOut()}
        dialog!!.rlNo.setOnClickListener { dialog!!.dismiss() }
        dialog!!.rlLogOut.setOnClickListener { dialog!!.dismiss() }
        dialog!!.show()
    }
    private fun logOut(){
     /*   val mpref=  SharaGoPref.getInstance(requireActivity())
        mpref.setLoginToken("")
        mpref.setFcmKey("")
        mpref.clear()*/
        mViewModel.logoutParam(SharaGoPref.getInstance(requireActivity()).getLoginToken("").toString())

        mViewModel.lLogOut.observe(requireActivity()){
            when(it){
                is ExceptionHandler.Loading->{

                }
                is ExceptionHandler.Success->{

                    try {
                        SharaGoPref.getInstance(requireActivity()).clear()
                        val i = Intent(requireActivity(), VendorLoginActivty::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(i)

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

}