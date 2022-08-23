package com.lukabaia.yummy.ui.fragments.detailed

import android.text.Html
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lukabaia.yummy.adapter.IngredientsAdapter
import com.lukabaia.yummy.databinding.FragmentDetailedBinding
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
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

    override fun listeners() { }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsFlow.collect {
                binding.apply {
                    Glide.with(binding.root).load(it?.image).into(ivRecipe)
                    tvTitle.text = it?.title
                    tvSummary.text = Html.fromHtml(it?.summary, 1)
                }
                ingredientsAdapter.submitList(it?.extendedIngredients)
            }
        }
    }
}