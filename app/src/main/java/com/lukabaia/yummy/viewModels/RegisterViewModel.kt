package com.lukabaia.yummy.viewModels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lukabaia.yummy.utils.ResultOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = database.getReference("userInfo")

    private var _registrationStatus = MutableStateFlow<ResultOf<String>>(ResultOf.Success())
    var registrationStatus = _registrationStatus.asStateFlow()

    fun signUp(email: String, password: String,userInfo: com.lukabaia.yummy.model.UserInfo) {
        viewModelScope.launch {
            signUpResponse(email, password, userInfo).collect{
                _registrationStatus.value = it
            }
        }
    }

    private fun signUpResponse(email: String, password: String, userInfo: com.lukabaia.yummy.model.UserInfo) = flow {
        try {
            auth?.let { authentication ->
                authentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            databaseReference.child(auth.currentUser?.uid.toString())
                                .setValue(userInfo)
                            flow{emit(ResultOf.Success<String>())}
                        } else {
                            flow{emit(ResultOf.Failure("failed"))}
                        }
                    }
            }
        } catch (e: Throwable) {
            emit(ResultOf.Failure(message = e.message ?: "error"))
        }
    }
}










