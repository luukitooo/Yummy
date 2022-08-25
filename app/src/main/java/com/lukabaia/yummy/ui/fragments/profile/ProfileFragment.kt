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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    private var imageUri: Uri? = null
    private var storage : FirebaseStorage? = null
    private var storageReference = Firebase.storage
    private var auth : FirebaseAuth? = null
    private val PICK_IMAGE_REQUEST = 71


    override fun listeners() {
//        binding.tvChange.setOnClickListener {
//
//            binding.btnSelectImage.isVisible = true
//        }

//        binding.btnSelectImage.setOnClickListener {
//
//            selectImage()
//
//        }
//        binding.btnUpdate.setOnClickListener {
//
//            uploadImage()
//
//        }
    }
//
//    private fun selectImage() {
//        val intent = Intent()
//        intent.type = "image/"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//
//            if(data == null || data.data == null){
//                return
//            }
//
//            imageUri = data.data
//            binding.imgProfileImage.setImageURI(imageUri)
//        }
//    }
//
//    private fun uploadImage() {
//
//        imageUri?.let {
//            binding.progressBar.isVisible = true
//            storageReference.getReference("Images").child(System.currentTimeMillis().toString())
//                .putFile(it)
//                .addOnSuccessListener { task->
//                    task.metadata!!.reference!!.downloadUrl
//                        .addOnSuccessListener {
//                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
//                            val imageMap = mapOf("url" to imageUri.toString())
//                            val databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
//                            databaseReference.child(uid).setValue(imageMap)
//                                .addOnSuccessListener {
//                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
//                                    binding.progressBar.isVisible = false
//                                }
//                                .addOnFailureListener {
//                                    binding.progressBar.isVisible = false
//                                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
//                                }
//                        }
//                }
//        }
//    }

    override fun init() {
//        auth = FirebaseAuth.getInstance()
//        storage = FirebaseStorage.getInstance()
//        storageReference = FirebaseStorage.getInstance()


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
                            delay(5000)
                            binding.progressBar.isVisible = false
                        }
                        is ResultOf.Failure -> {
                            d("realtime", "data error")
                        }
                    }
                }
            }
        }
    }
}