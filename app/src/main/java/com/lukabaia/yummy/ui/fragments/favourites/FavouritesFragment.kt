package com.lukabaia.yummy.ui.fragments.favourites

import androidx.fragment.app.viewModels
import com.lukabaia.yummy.databinding.FragmentFavouritesBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.FavouritesViewModel

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(FragmentFavouritesBinding::inflate) {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
    }

    override fun observers() {
    }

}