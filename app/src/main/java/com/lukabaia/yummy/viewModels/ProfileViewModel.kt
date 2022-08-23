package com.lukabaia.yummy.viewModels

import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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

    private var _dataStatus = MutableStateFlow<ResultOf<String>>(ResultOf.Success())
    var dataStatus = _dataStatus.asStateFlow()

    fun showRealtimeData(username: TextView, email: TextView) {
        viewModelScope.launch {
            showData(username, email).collect {
                _dataStatus.value = it
            }
        }
    }

    private fun showData(username: TextView, email: TextView) = flow {
        try {
            if (auth.currentUser?.uid.toString().isNotEmpty()) {
                databaseReference.child(auth.currentUser?.uid.toString())
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userInfo = snapshot.getValue(UserInfo::class.java)!!
                            username.text = userInfo.username
                            email.text = userInfo.email
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