package com.lukabaia.yummy.ui.fragments.searchResult

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.lukabaia.yummy.adapter.RecipeAdapter
import com.lukabaia.yummy.databinding.FragmentSearchBinding
import com.lukabaia.yummy.extensions.hideKeyboard
import com.lukabaia.yummy.extensions.showKeyboardFor
import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModel<SearchViewModel>()

    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnSearch.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getRecipesByTitle(
                    title = binding.etSearch.text.toString()
                )
            }
            requireActivity().hideKeyboard()
        }
        recipeAdapter.onItemClickListener = {
            findNavController().navigate(SearchFragmentDirections.toDetailedFragment(it.id ?: -1))
        }
    }

    override fun init() = with(binding) {
        etSearch.requestFocusFromTouch()
        requireContext().showKeyboardFor(etSearch)
        rvSearch.adapter = recipeAdapter
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collect { handler ->
                handleResponse(handler)
            }
        }
    }

    private fun handleResponse(handler: ResponseHandler) {
        when(handler) {
            is ResponseHandler.Success<*> -> {
                recipeAdapter.submitList(
                    (handler.result as SearchedRecipesInfo).results ?: emptyList()
                )
            }
            is ResponseHandler.Error -> {
                Snackbar.make(requireView(), handler.errorMessage, Snackbar.LENGTH_LONG).show()
            }
            is ResponseHandler.Loader -> {
                binding.progressBar.isVisible = handler.isLoading
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.etSearch.setText("")
    }

}