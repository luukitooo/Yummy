package com.lukabaia.yummy.ui.fragments.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lukabaia.yummy.adapter.CategoryAdapter
import com.lukabaia.yummy.adapter.RecipeAdapter
import com.lukabaia.yummy.databinding.FragmentHomeBinding
import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.RecipeTypes
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModel<HomeViewModel>()

    private val categoryAdapter by lazy { CategoryAdapter() }
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun init() {

        buildRecyclers()
    }

    override fun listeners() {
        categoryAdapter.onItemClickListener = {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.setType(it)
            }
        }
        recipeAdapter.onItemClickListener = {
            findNavController().navigate(HomeFragmentDirections.toDetailedFragment(it.id ?: -1))
        }

        binding.btnRandomizer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRandomFragment())
        }
        binding.etSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.toSearchFragment())
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collect {
                handleResponse(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoryFlow.collect {
                getData(it.value)
            }
        }
    }

    private fun handleResponse(handler: ResponseHandler) {
        when(handler) {
            is ResponseHandler.Success<*> -> {
                recipeAdapter.submitList((handler.result as SearchedRecipesInfo).results)
            }
            is ResponseHandler.Loader -> {
                binding.progressBar.isVisible = handler.isLoading
            }
            is ResponseHandler.Error -> {
                Log.d("errorMessage", handler.errorMessage)
            }
        }
    }

    private fun buildRecyclers() = with(binding) {
        rvCategories.adapter = categoryAdapter
        rvRecipes.adapter = recipeAdapter
        categoryAdapter.submitList(RecipeTypes.values().toList())
    }

    private fun getData(type: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSearchResult(
                type = type,
                number = 5
            )
        }
    }

}