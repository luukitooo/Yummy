package com.lukabaia.yummy.ui.fragments.detailed

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lukabaia.yummy.R
import com.lukabaia.yummy.adapter.IngredientsAdapter
import com.lukabaia.yummy.databinding.FragmentDetailedBinding
import com.lukabaia.yummy.extensions.setTint
import com.lukabaia.yummy.model.FavoriteRecipe
import com.lukabaia.yummy.model.network.DetailedRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.DetailedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedFragment : BaseFragment<FragmentDetailedBinding>(FragmentDetailedBinding::inflate) {

    private var isSaved = false

    private lateinit var image: String

    private val viewModel by viewModel<DetailedViewModel>()

    private val args: DetailedFragmentArgs by navArgs()

    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    override fun init() {
        binding.rvIngredients.adapter = ingredientsAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRecipeDetails(args.id)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnSave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                handleSaving(getRecipe())
                isSaved = !isSaved
            }
        }
        btnPlay.setOnClickListener {
            searchInYoutube(
                query = binding.tvTitle.text.toString()
            )
        }
    }

    private suspend fun handleSaving(recipe: FavoriteRecipe) {
        when(isSaved) {
            true -> {
                viewModel.removeRecipe(recipe)
                binding.btnSave.setTint(requireContext(), R.color.black)
            }
            false -> {
                viewModel.addRecipe(recipe)
                binding.btnSave.setTint(requireContext(), R.color.orange)
            }
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsFlow.collect {
                handleResponse(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect { favoritesList ->
                val recipe = favoritesList.find { it.id == args.id }
                recipe?.let {
                    isSaved = true
                    binding.btnSave.setTint(requireContext(), R.color.orange)
                }
            }
        }
    }

    private fun handleResponse(handler: ResponseHandler) {
        when(handler) {
            is ResponseHandler.Success<*> -> {
                val result = handler.result as DetailedRecipesInfo
                image = result.image ?: ""
                binding.apply {
                    Glide.with(binding.root).load(result.image).into(ivRecipe)
                    tvTitle.text = result.title
                    tvSummary.text = Html.fromHtml(result.summary, 1)
                    btnSave.visibility = View.VISIBLE
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

    private fun getRecipe() = FavoriteRecipe(
        id = args.id,
        title = binding.tvTitle.text.toString(),
        image = image
    )

    private fun searchInYoutube(query: String) {
        val intent = Intent(Intent.ACTION_SEARCH)
        intent.apply {
            setPackage("com.google.android.youtube")
            putExtra("query", query)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

}