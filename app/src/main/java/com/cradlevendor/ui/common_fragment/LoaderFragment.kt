package com.cradlevendor.ui.common_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cradlevendor.R


class LoaderFragment : DialogFragment() {

    companion object {


        fun showLoader(fragmentManager: FragmentManager){
            try {
                LoaderFragment().show(fragmentManager,"LoaderFragment")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun dismissLoader(fragmentManager: FragmentManager){
            try {
                (fragmentManager.findFragmentByTag("LoaderFragment") as DialogFragment).dismiss()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_loader, container, false)
    }

}