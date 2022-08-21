package com.lukabaia.yummy.ui.fragments.login

import androidx.fragment.app.viewModels
import com.lukabaia.yummy.databinding.FragmentLoginBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.FavouritesViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun listeners() {
//        binding.tvLogin.setOnClickListener {
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
//        }
    }

    override fun init() {
    }

    override fun observers() {
    }


}