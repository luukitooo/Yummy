package com.lukabaia.yummy.ui.fragments

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentLoginBinding
import com.lukabaia.yummy.ui.activities.MainActivity
import com.lukabaia.yummy.ui.fragments.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun listeners() {

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
            when {
                isEmptyField() -> {
                    Snackbar.make(binding.root,
                        getString(R.string.empty_fields_error),
                        Snackbar.LENGTH_LONG)
                        .show()
                }
                !isValidEmail() -> {
                    Snackbar.make(binding.root,
                        getString(R.string.invalid_email_error),
                        Snackbar.LENGTH_LONG)
                        .show()
                }
                else -> {
                    login()
                }
            }
        }
    }

    private fun login() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                } else {
                    Snackbar.make(binding.root,
                        getString(R.string.login_error),
                        Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty()
    }

    override fun init() {
    }

    override fun observers() {

    }
}