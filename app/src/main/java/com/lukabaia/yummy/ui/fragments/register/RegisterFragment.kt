package com.lukabaia.yummy.ui.fragments.register

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
                    Snackbar.make(binding.root,
                        getString(R.string.empty_fields_error),
                        Snackbar.LENGTH_LONG).show()
                }
                !isValidEmail() -> {
                    Snackbar.make(binding.root,
                        getString(R.string.invalid_email_error),
                        Snackbar.LENGTH_LONG).show()
                }
                isNotValidPassword() -> {
                    Snackbar.make(binding.root,
                        getString(R.string.password_length_limit),
                        Snackbar.LENGTH_LONG).show()
                }
                !passwordsMatch() -> {
                    Snackbar.make(binding.root, getString(R.string.pass_not_match), Snackbar.LENGTH_LONG)
                        .show()
                }
                isNotValidUsername() -> {
                    Snackbar.make(binding.root, getString(R.string.first_char_number_error), Snackbar.LENGTH_LONG)
                        .show()
                }
                else -> {
                    doRegistration()
                    observerRegistration()
                }
            }
        }
    }

    private fun isNotValidPassword(): Boolean = with(binding) {
        return@with binding.etPassword.text.toString().length < 8
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty()
    }
    override fun observers() {
    }
    private fun isNotValidUsername() : Boolean = with(binding){
        return@with binding.etUsername.text.toString()[0].isDigit()

    }

    private fun passwordsMatch(): Boolean = with(binding) {
        return@with binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString()
    }



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
                            requireActivity().onBackPressed()
                        }
                        is ResultOf.Failure -> {
                            Snackbar.make(binding.root, getString(R.string.already_registered), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    override fun init() {
    }


}