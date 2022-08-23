package com.lukabaia.yummy.ui.fragments.profile

import android.util.Log.d
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.NoInternetViewModel
import com.lukabaia.yummy.viewModels.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
        // database
        showData()
    }

    private fun showData(){
        val userName = binding.tvEmail
        val email = binding.tvEmail
        viewModel.showRealtimeData(userName, email)
    }


    override fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataStatus.collect {
                    when (it) {
                        is ResultOf.Success -> {
                            d("realtime", "data showed")
                        }
                        is ResultOf.Failure -> {
                            d("realtime", "data error")
                        }
                    }
                }
            }
        }
    }

//    private val auth = FirebaseAuth.getInstance()
//    private val database = FirebaseDatabase.getInstance().getReference("userInfo")

//    override fun init() {
//        // database
//        if (auth.currentUser?.uid.toString().isNotEmpty()) {
//            getUserData()
//        }
//    }
//
//    private fun getUserData() {
//        database.child(auth.currentUser?.uid.toString())
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val userInfo = snapshot.getValue(UserInfo::class.java)!!
//                    binding.tvUsername.text = userInfo.username
//                    binding.tvEmail.text = userInfo.email
//                }
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//    }
}