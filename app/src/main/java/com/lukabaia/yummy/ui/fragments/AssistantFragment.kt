package com.lukabaia.yummy.ui.fragments

import com.lukabaia.yummy.databinding.FragmentAssistantBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment

class AssistantFragment : BaseFragment<FragmentAssistantBinding>(FragmentAssistantBinding::inflate) {

    override fun init() {

    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observers() {

    }

}