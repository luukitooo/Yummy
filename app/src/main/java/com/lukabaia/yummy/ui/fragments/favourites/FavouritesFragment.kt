package com.lukabaia.yummy.ui.fragments.favourites

import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lukabaia.yummy.R
import com.lukabaia.yummy.adapter.FavoritesAdapter
import com.lukabaia.yummy.databinding.FragmentFavouritesBinding
import com.lukabaia.yummy.model.FavoriteRecipe
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.FavouritesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment :
    BaseFragment<FragmentFavouritesBinding>(FragmentFavouritesBinding::inflate) {

    private val viewModel by viewModel<FavouritesViewModel>()

    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override fun listeners() {
        favoritesAdapter.onItemClickListener = {
            findNavController().navigate(
                FavouritesFragmentDirections.actionFavouritesFragmentToDetailedFragment2(
                    id = it.id ?: -1
                )
            )
        }
        binding.btnAssistant.setOnClickListener {
            findNavController().navigate(FavouritesFragmentDirections.toAssistantFragment())
        }
    }

    override fun init() {
        binding.rvFavorites.adapter = favoritesAdapter
        ItemTouchHelper(favoritesSwipeCallback).attachToRecyclerView(binding.rvFavorites)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect {
                handleResponse(it)
            }
        }
    }

    private fun handleResponse(handler: ResponseHandler) {
        when (handler) {
            is ResponseHandler.Success<*> -> {
                favoritesAdapter.submitList(
                    handler.result as List<FavoriteRecipe>
                )
            }
            is ResponseHandler.Error -> {
                Snackbar.make(binding.root, handler.errorMessage, Snackbar.LENGTH_LONG).show()
            }
            is ResponseHandler.Loader -> {
                binding.progressBar.isVisible = handler.isLoading
            }
        }
    }

    private val favoritesSwipeCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val title = viewHolder.itemView.tag as String
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeRecipeByTitle(title)
                viewModel.getFavorites()
            }
        }
    }

}