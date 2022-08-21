package com.lukabaia.yummy.ui.fragments.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentFavouritesBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(FragmentFavouritesBinding::inflate) {

    private val viewModel: FavouritesViewModel by viewModels()

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