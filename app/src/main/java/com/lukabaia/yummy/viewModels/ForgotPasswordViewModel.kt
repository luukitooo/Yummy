package com.lukabaia.yummy.viewModels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lukabaia.yummy.utils.ResultOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _passRestore = MutableStateFlow<ResultOf<String>>(ResultOf.Success())
    var passRestore = _passRestore.asStateFlow()

    fun restore(email: String) {
        viewModelScope.launch {
            restoreResponse(email).collect{
                _passRestore.value = it
            }
        }
    }

    private fun restoreResponse(email: String) = flow {
        try {
            auth?.let {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    ?.addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            flow{emit(ResultOf.Success<String>())}
                        }
                        else{
                            flow{emit(ResultOf.Failure("failed"))}
                        }
                    }
            }
        } catch (e: Throwable) {
            emit(ResultOf.Failure(message = e.message ?: "error"))
        }
    }
}