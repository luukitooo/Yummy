package com.lukabaia.yummy.ui.fragments.detailed

import android.text.Html
import android.util.Log
import android.view.View
import android.view.View.inflate
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lukabaia.yummy.adapter.IngredientsAdapter
import com.lukabaia.yummy.databinding.FragmentDetailedBinding
import com.lukabaia.yummy.model.network.DetailedRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.DetailedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedFragment : BaseFragment<FragmentDetailedBinding>(FragmentDetailedBinding::inflate) {

    private val viewModel by viewModel<DetailedViewModel>()

    private val args: DetailedFragmentArgs by navArgs()

    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    override fun init() {
        binding.rvIngredients.adapter = ingredientsAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRecipeDetails(args.id)
        }
    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsFlow.collect {
                handleResponse(it)
            }
        }
    }

    private fun handleResponse(handler: ResponseHandler) {
        when(handler) {
            is ResponseHandler.Success<*> -> {
                val result = handler.result as DetailedRecipesInfo
                binding.apply {
                    Glide.with(binding.root).load(result.image).into(ivRecipe)
                    tvTitle.text = result.title
                    tvSummary.text = Html.fromHtml(result.summary, 1)
                }
                ingredientsAdapter.submitList(result.extendedIngredients)
            }
            is ResponseHandler.Error -> {
                Log.d("errorMessage", handler.errorMessage)
            }
            is ResponseHandler.Loader -> {
                if (!handler.isLoading) {
                    binding.linearLayout.forEach {
                        it.visibility = View.VISIBLE
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}