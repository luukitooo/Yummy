package com.lukabaia.yummy.ui.fragments

import androidx.navigation.fragment.findNavController
import com.lukabaia.yummy.databinding.FragmentWelcomeBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

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