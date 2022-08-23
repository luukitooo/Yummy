package com.lukabaia.yummy.ui.fragments.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.util.Log.d
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.ProfileViewModel
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    private var imageUri: Uri? = null
    private var firebaseStore : FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var auth : FirebaseAuth? = null
    private val PICK_IMAGE_REQUEST = 71

    override fun listeners() {

        binding.tvChange.setOnClickListener {

            binding.btnSelectImage.isVisible = true
            binding.btnUploadImage.isVisible = true
        }

        binding.btnSelectImage.setOnClickListener {

            selectImage()

        }
        binding.btnUploadImage.setOnClickListener {

            uploadImage()


        }

    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

            if(data == null || data.data == null){
                return
            }

            imageUri = data.data
            binding.imgProfileImage.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {

        if(imageUri != null){
            val reference = storageReference?.child(auth!!.currentUser!!.uid)
            val uploadTask = reference?.putFile(imageUri!!)
            Toast.makeText(context, "Successfully uploaded!", Toast.LENGTH_SHORT).show()
            binding.btnSelectImage.isVisible = false
            binding.btnUploadImage.isVisible = false
        } else{
            Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()

        }

    }

    override fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference.child("Profile Img")
        // database
        showData()
        observers()
    }

    private fun showData() {
        val userName = binding.tvUsername
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