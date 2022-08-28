package com.lukabaia.yummy.ui.fragments.randomizer


import android.text.Html
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.FragmentRandomBinding
import com.lukabaia.yummy.model.network.RandomRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.RandomViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomFragment : BaseFragment<FragmentRandomBinding>(FragmentRandomBinding::inflate) {

    private  val viewModel by viewModel<RandomViewModel>()

    override fun listeners() {

        binding.btnGenerate.setOnClickListener {
            observer()
            getData()
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(RandomFragmentDirections.actionRandomFragmentToHomeFragment())
        }
    }

    override fun init() {

    }

     private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getData()
                viewModel.recipeFlow.collect{
                    when(it) {
                        is ResponseHandler.Success<*> -> {
                            val result = (it.result as MutableList<RandomRecipesInfo.RandomRecipe>)[0]
                            binding.apply {
                                tvRecipeTitle.text = result.title.toString()
                                tvSummary.text = Html.fromHtml(result.summary, 2)
                                    .substring(0, 100).plus("...")
                                tvReadyInMinutes.text = result.readyInMinutes.toString()
                                tvVegan.text = result.vegan.toString()
                                tvCheap.text = result.cheap.toString()
                                tvScore.text = result.healthScore.toString()
                                tvPopular.text = result.veryPopular.toString()
                                Glide.with(ivRecipe).load(result.image).into(binding.ivRecipe)
                                itemCardView.setOnClickListener {
                                    findNavController().navigate(RandomFragmentDirections.actionRandomFragmentToDetailedFragment((result.id ?: -1).toInt()))
                                }
                            }
                            showViews()
                        }
                        is ResponseHandler.Error -> {
                            Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loader -> {
                            binding.progressBar.isVisible = it.isLoading
                        }
                    }
                }
            }
        }
    }

    private fun showViews() {
        binding.itemCardView.isVisible = true
        binding.clRecipe.forEach {
            it.isVisible = true
        }
    }

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getRandomRecipe(
                    number = 1
                )
            }
        }
    }
    override fun observers() {
    }
}