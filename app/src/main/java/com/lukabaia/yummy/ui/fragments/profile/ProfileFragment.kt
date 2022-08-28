package com.lukabaia.yummy.ui.fragments.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log.d
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.model.Image
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.activities.AuthActivity
import com.lukabaia.yummy.ui.activities.MainActivity
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.ProfileViewModel
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

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