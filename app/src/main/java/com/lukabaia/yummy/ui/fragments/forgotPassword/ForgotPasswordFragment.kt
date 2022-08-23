package com.lukabaia.yummy.ui.fragments.forgotPassword

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentForgotPasswordBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.ui.fragments.login.LoginFragmentDirections
import com.lukabaia.yummy.utils.ResultOf
import com.lukabaia.yummy.viewModels.ForgotPasswordViewModel
import kotlinx.coroutines.launch

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun listeners() {

        binding.btnSend.setOnClickListener {
            when {
                isEmptyField() -> Toast.makeText(context, getString(R.string.empty_fields_error), Toast.LENGTH_SHORT).show()
                else ->
                    doRestore()
            }
            observerPassRestore()
        }
    }
    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty()
    }

    override fun init() {
    }

    override fun observers() {
    }

    private fun doRestore(){
        val email = binding.etEmail.text.toString()
        viewModel.restore(email)
    }

    private fun observerPassRestore(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passRestore.collect{
                    when (it) {
                        is ResultOf.Success -> {
                            Toast.makeText(context, getString(R.string.recovery_success), Toast.LENGTH_SHORT).show()
                            findNavController().navigate(ForgotPasswordFragmentDirections.toLoginFragment())
                        }
                        is ResultOf.Failure -> {
                            Toast.makeText(context, getString(R.string.recovery_error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}