package com.lukabaia.yummy.ui.fragments.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lukabaia.yummy.adapter.CategoryAdapter
import com.lukabaia.yummy.adapter.RecipeAdapter
import com.lukabaia.yummy.databinding.FragmentHomeBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.RecipeTypes
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
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collect {
                recipeAdapter.submitList(it?.results ?: emptyList())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoryFlow.collect {
                getData(it.value)
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