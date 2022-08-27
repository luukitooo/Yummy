package com.lukabaia.yummy.viewModels

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.lukabaia.yummy.model.Image
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.utils.ResultOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = database.getReference("userInfo")
    private val databaseReferenceImage: DatabaseReference = database.getReference("userImages")


    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageReference = Firebase.storage.getReference("Images")

    private var _dataStatus = MutableStateFlow<ResultOf<String>>(ResultOf.Success())
    var dataStatus = _dataStatus.asStateFlow()

    fun showRealtimeData(username: TextView, email: TextView,imageView: ImageView) {
        viewModelScope.launch {
            showData(username, email, imageView).collect {
                _dataStatus.value = it
            }
        }
    }

    private fun showData(username: TextView, email: TextView, imageView: ImageView) = flow {
        try {
            if (auth.currentUser?.uid.toString().isNotEmpty()) {
                databaseReference.child(auth.currentUser?.uid.toString())
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userInfo = snapshot.getValue(UserInfo::class.java)!!
                            username.text = "User: ${userInfo.username}"
                            email.text = userInfo.email
                            flow { emit(ResultOf.Success<String>()) }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                databaseReferenceImage.child(auth.currentUser?.uid.toString())
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val image = snapshot.getValue(Image::class.java)!!
                            imageView.setImageURI(image.imageUri)
                            flow { emit(ResultOf.Success<String>()) }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
            }
        } catch (e: Throwable) {
            emit(ResultOf.Failure(message = e.message ?: "error"))
        }
    }
}