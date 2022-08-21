package com.lukabaia.yummy.ui.fragments.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentRegisterBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.ui.fragments.noInternet.NoInternetViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun listeners() {
        TODO("Not yet implemented")
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun observers() {
        TODO("Not yet implemented")
    }

}