package com.cradlevendor.chat.model

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.cradlevendor.application.ApplicationClass
import com.cradlevendor.base_utils.BaseActivity
import com.cradlevendor.databinding.ActivityFbUserListBinding
import com.cradlevendor.ui.adapter.UserListAdapter
import com.cradlevendor.utils.SharaGoPref
import com.cradlevendor.viewmodel.MainViewModel
import com.cradlevendor.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_fb_user_list.*
import org.json.JSONObject

class FbUserListActivity: BaseActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityFbUserListBinding

    private var userList: ArrayList<String?> = ArrayList()
    private var userListModel: ArrayList<UserModelList?> = ArrayList()
    private var idFriends: ArrayList<CharSequence>? = null
    private var myId = ""
    private var consersation: Consersation? = null
    private var progress: ProgressDialog? = null
    private var fromId: String? = null
    private var isChat = true
    private var pref: SharaGoPref? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val response = (application as ApplicationClass).repository
        mBinding =ActivityFbUserListBinding .inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel =
            ViewModelProvider(this, MainViewModelFactory(response))[MainViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        findIDEmail(intent.getStringExtra("id").toString())
        iv_back.setOnClickListener { finish() }

    }
    var newemail:String?=null
    private fun findIDEmail(email11: String) {
        FirebaseDatabase.getInstance().reference.child("users")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    if (dataSnapshot.value != null) {
                        var key: String?
                        progress?.dismiss()
                        val email = ((dataSnapshot.value as HashMap<*, *>?))!!["emailMobile"] as String?
                        if (dataSnapshot.key == email11) {
                            if (isChat) {
                                isChat = false
                                val id = dataSnapshot.key.toString()
                                try {
                                    val jsonObject=JSONObject(dataSnapshot.child("conversations").value as HashMap<*,*>)
                                    val iterator = jsonObject.keys()
                                    while (iterator.hasNext()) {
                                        key = iterator.next()
                                        if(SharaGoPref.getInstance(this@FbUserListActivity).getVideoID("").toString().equals(key)){

                                        }else{

                                            val lastMessage = (dataSnapshot.child("conversations").child(key).child("lastMessage")).value
                                            //val lastMessage=dataSnapshot.child("conversations").child("lastMessage").value
                                            val location=(dataSnapshot.child("conversations").child(key).child("location")).value
                                            val enId=key
                                            val userName=dataSnapshot.child("conversations").child(key).child("metaData").child("user").child("name").value
                                            FirebaseDatabase.getInstance().reference.child("users")
                                                .addChildEventListener(object : ChildEventListener {
                                                    override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                                                        //   showToast(dataSnapshot.key)
                                                        if (dataSnapshot.key==key) {
                                                            var key: String? = null
                                                            var key2: String? = null
                                                            // .iterator().next().toString()
                                                            progress?.dismiss()
                                                            newemail = ((dataSnapshot.value as HashMap<*, *>?))!!["emailMobile"] as String?
                                                            val userModelList=  UserModelList(lastMessage.toString(),location.toString(),enId.toString(),userName.toString(),newemail.toString())
                                                            userListModel.add(userModelList)
                                                            userList.add(key)
                                                            Log.e("userList",userListModel.toString())
                                                            //  showToast(newemail)

                                                        }


                                                    }
                                                    override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                                                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                                                    override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                                                    override fun onCancelled(databaseError: DatabaseError) {}
                                                })





                                        }

                                        Log.e("arrayList",userList.toString(),)
                                    }

                                    mBinding.mUserListAdapter= UserListAdapter(this@FbUserListActivity,userList,userListModel)

                                    // Log.e("iterator",iterator.toString())

                                    pref?.setGoogleID(id)
                                    val idF = ArrayList<CharSequence>()
                                    idF.add(id)
                                    idFriends = idF
                                    myId = pref?.getGoogleID("").toString()
                                }catch (e:Exception){
                                }


                             /*   if (idFriends != null) {
                                    try {
                                        setAdapter(consersation!!)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }*/
                            }
                        }
                    }
                }
                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })

    }




    override fun onClick(p0: View?) {
    }

}

