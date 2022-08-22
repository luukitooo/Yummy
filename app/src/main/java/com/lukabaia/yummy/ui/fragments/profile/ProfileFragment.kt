package com.lukabaia.yummy.ui.fragments.profile

import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.NoInternetViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: NoInternetViewModel by viewModels()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("userInfo")

    override fun listeners() {
    }

    override fun init() {
        // database
        if (auth.currentUser?.uid.toString().isNotEmpty()) {
            getUserData()
        }
    }

    private fun getUserData() {
        database.child(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = snapshot.getValue(UserInfo::class.java)!!
                    binding.tvUsername.text = userInfo.username
                    binding.tvEmail.text = userInfo.email
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    override fun observers() {
    }
}