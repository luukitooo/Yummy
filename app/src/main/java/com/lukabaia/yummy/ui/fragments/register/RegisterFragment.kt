package com.lukabaia.yummy.ui.fragments.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentRegisterBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("userInfo")

    override fun listeners() {

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.btnSignUp.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val userInfo = UserInfo(userName, email, password)
            when {
                isEmptyField() -> {
                    Toast.makeText(context,
                        getString(R.string.empty_fields_error),
                        Toast.LENGTH_SHORT).show()
                }
                !isValidEmail() -> {
                    Toast.makeText(context, getString(R.string.invalid_email_error), Toast.LENGTH_SHORT).show()
                }
                isValidPassword() -> {
                    Toast.makeText(context,
                        getString(R.string.password_length_limit),
                        Toast.LENGTH_SHORT).show()
                }
                passwordsMatch() -> {
                    Toast.makeText(context, getString(R.string.pass_not_match), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                database.child(auth.currentUser?.uid.toString()).setValue(userInfo)
                                Toast.makeText(context,
                                    getString(R.string.success),
                                    Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context,
                                    getString(R.string.error),
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }
    }

//    private fun register() {
//
//
//    }

    private fun isValidPassword(): Boolean = with(binding) {
        return@with binding.etPassword.text.toString().length < 8
    }


    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty()
    }

    private fun passwordsMatch(): Boolean = with(binding) {
        return@with binding.etPassword.text == binding.etRepeatPassword.text
    }

    override fun init() {
    }

    override fun observers() {
    }

}