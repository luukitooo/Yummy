package com.lukabaia.yummy.ui.fragments.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentRegisterBinding
import com.lukabaia.yummy.model.UserInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun listeners() {

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnSignUp.setOnClickListener {

            when {
                isEmptyField() -> {
                    Toast.makeText(context,
                        getString(R.string.empty_fields_error),
                        Toast.LENGTH_SHORT).show()
                }
                !isValidEmail() -> {
                    Toast.makeText(context,
                        getString(R.string.invalid_email_error),
                        Toast.LENGTH_SHORT).show()
                }
                isValidPassword() -> {
                    Toast.makeText(context,
                        getString(R.string.password_length_limit),
                        Toast.LENGTH_SHORT).show()
                }
                passwordsMatch() -> {
                    Toast.makeText(context, getString(R.string.pass_not_match), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    doRegistration()
                }
            }
            observerRegistration()
        }
    }

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

    override fun observers() {}

    private fun doRegistration() {
        val userName = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val userInfo = UserInfo(userName, email, password)
        viewModel.signUp(email, password, userInfo)
    }

    private fun observerRegistration() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registrationStatus.collect {
                    when (it) {
                        is ResultOf.Success -> {
                            Toast.makeText(context, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                        }
                        is ResultOf.Failure -> {
                            Toast.makeText(context, getString(R.string.already_registered), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}