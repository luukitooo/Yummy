package com.lukabaia.yummy.ui.fragments.randomizer


import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.lukabaia.yummy.adapter.RandomAdapter
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
                            val itemImage = (it.result as MutableList<RandomRecipesInfo.RandomRecipe>)[0].image
                            val itemTitle = (it.result as MutableList<RandomRecipesInfo.RandomRecipe>)[0].title
                            val itemId = (it.result as MutableList<RandomRecipesInfo.RandomRecipe>)[0].id
                            binding.tvRecipeTitle.text = itemTitle.toString()
                            Glide.with(binding.ivRecipe).load(itemImage).into(binding.ivRecipe)
                            binding.itemCardView.setOnClickListener {
                                findNavController().navigate(RandomFragmentDirections.actionRandomFragmentToDetailedFragment((itemId ?: -1).toInt()))
                            }
                        }
                        is ResponseHandler.Error -> {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loader -> {
                            binding.progressBar.isVisible = it.isLoading
                        }
                    }
                }
            }
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