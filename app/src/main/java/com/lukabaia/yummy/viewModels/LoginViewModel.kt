package com.lukabaia.yummy.viewModels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.lukabaia.yummy.R
import com.lukabaia.yummy.ui.fragments.login.LoginFragmentDirections
import com.lukabaia.yummy.utils.ResultOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _loginStatus = MutableStateFlow<ResultOf<String>>(ResultOf.Failure())
    var loginStatus = _loginStatus.asStateFlow()

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInResponse(email, password).collect{
                _loginStatus.value = it
            }
        }
    }

    private fun logInResponse(email: String, password: String) = flow {
        try {
            auth?.let {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
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