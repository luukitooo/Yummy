package com.lukabaia.yummy.ui.fragments.searchResult

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentSearchBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.ui.fragments.noInternet.NoInternetViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

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