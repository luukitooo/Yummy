package com.lukabaia.yummy.ui.fragments.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentLoginBinding
import com.lukabaia.yummy.ui.activities.MainActivity
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.FavouritesViewModel
import com.lukabaia.yummy.viewModels.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

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
                   doLogIn()

                }
            }
            observerLogIn()
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

    private fun doLogIn(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.logIn(email, password)
    }

    private fun observerLogIn(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginStatus.collect{
                    when (it) {
                        is ResultOf.Success -> {
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                        }
                        is ResultOf.Failure -> {
                            Snackbar.make(binding.root, getString(R.string.login_error), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}