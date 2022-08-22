package com.lukabaia.yummy.ui.fragments.forgotPassword

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentForgotPasswordBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.ForgotPasswordViewModel

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun listeners() {

        binding.btnSend.setOnClickListener {
            when {
                isEmptyField() -> Toast.makeText(context, getString(R.string.empty_fields_error), Toast.LENGTH_SHORT).show()
                else -> {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(binding.etEmail.text.toString())
                        ?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty()
    }

    override fun init() {
    }

    override fun observers() {
    }

}