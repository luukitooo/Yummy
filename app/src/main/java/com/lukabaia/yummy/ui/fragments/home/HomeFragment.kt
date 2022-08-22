package com.lukabaia.yummy.ui.fragments.home

import androidx.lifecycle.lifecycleScope
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

        getData(RecipeTypes.values()[0].value)

    }

    override fun listeners() {
        categoryAdapter.onItemClickListener = {
            getData(it.value)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collect {
                recipeAdapter.submitList(it?.results ?: emptyList())
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