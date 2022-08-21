package com.lukabaia.yummy.ui.fragments.register

import androidx.fragment.app.viewModels
import com.lukabaia.yummy.databinding.FragmentRegisterBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun listeners() {
//        binding.tvSignUp.setOnClickListener {
//            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToWelcomeFragment())
//        }
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun observers() {
        TODO("Not yet implemented")
    }

}