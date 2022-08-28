package com.lukabaia.yummy.ui.fragments

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.activities.AuthActivity
import com.lukabaia.yummy.ui.fragments.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    override fun listeners() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ProfileFragment.requireContext(), AuthActivity::class.java))
        }
    }

    override fun init() {
        // database
        showData()
    }
    private fun showData() {
        databaseReference.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = snapshot.getValue(UserInfo::class.java) ?: return
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