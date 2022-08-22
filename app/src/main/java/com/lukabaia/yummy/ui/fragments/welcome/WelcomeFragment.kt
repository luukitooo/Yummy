package com.lukabaia.yummy.ui.fragments.welcome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lukabaia.yummy.databinding.FragmentWelcomeBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.WelcomeViewModel

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun listeners() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.toLoginFragment())
        }
    }

    override fun init() {

    }

    override fun observers() {

    }

}