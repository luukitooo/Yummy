package com.lukabaia.yummy.ui.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log.d
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.lukabaia.yummy.databinding.FragmentProfileBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.activities.AuthActivity
import com.lukabaia.yummy.ui.activities.MainActivity
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import java.io.ByteArrayOutputStream
import java.io.File

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val PICK_IMAGE_REQUEST = 71
    private var imageUri: Uri? = null
    private var storageReference = Firebase.storage.reference

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")

    private var storageRef =
        FirebaseStorage.getInstance().getReference("Images/${auth.currentUser?.uid!!}")


    override fun listeners() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ProfileFragment.requireContext(), AuthActivity::class.java))
        }
        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Images"), PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

            if (data == null || data.data == null) {
                return
            }
            imageUri = data.data
            binding.imgProfileImage.setImageURI(imageUri)
            binding.progressBar.isVisible = true
            (activity as MainActivity).disableNavBar()
            uploadImage()
        }
    }

    private fun uploadImage() {
        try {
            val bmp2 = binding.imgProfileImage.drawable.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bmp2.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val putbytes = storageRef.putBytes(byteArray)

            val task = putbytes.continueWithTask {
                storageRef.downloadUrl
            }.addOnCompleteListener {
                imageUri = it.result
                Toast.makeText(context, "image uploaded", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
                (activity as MainActivity).enableNavBar()

            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadImage() {

        try {
            val localFile: File = File.createTempFile("tempFile", "jpg")
            val uid = auth.currentUser?.uid!!

            storageReference = FirebaseStorage.getInstance().getReference("Images/$uid")
            storageReference.getFile(localFile).addOnSuccessListener {
                binding.apply {
                    val bitmap: Bitmap =
                        BitmapFactory.decodeFile(localFile.absolutePath)
                    imgProfileImage.setImageBitmap(bitmap)
                    binding.progressBar.isVisible = false

                }
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun init() {
        // database
        showData()
        try {
            downloadImage()
        }catch (e:Exception){
            binding.progressBar.isVisible = false
            d("log", "init crash")
        }
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