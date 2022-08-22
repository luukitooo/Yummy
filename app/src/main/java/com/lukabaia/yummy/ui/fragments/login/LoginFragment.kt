package com.lukabaia.yummy.ui.fragments.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentLoginBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.FavouritesViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun listeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                isEmptyField() -> {
                    Toast.makeText(context,
                        getString(R.string.empty_fields_error),
                        Toast.LENGTH_SHORT)
                        .show()
                }
                !isValidEmail() -> {
                    Toast.makeText(context,
                        getString(R.string.invalid_email_error),
                        Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            } else {
                                Toast.makeText(context,
                                    getString(R.string.login_error),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                }
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