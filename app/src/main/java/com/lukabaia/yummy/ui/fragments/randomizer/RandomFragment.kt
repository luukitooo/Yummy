package com.lukabaia.yummy.ui.fragments.randomizer


import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukabaia.yummy.adapter.RandomAdapter
import com.lukabaia.yummy.databinding.FragmentRandomBinding
import com.lukabaia.yummy.model.network.RandomRecipesInfo
import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.utils.ResponseHandler
import com.lukabaia.yummy.viewModels.HomeViewModel
import com.lukabaia.yummy.viewModels.RandomViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomFragment : BaseFragment<FragmentRandomBinding>(FragmentRandomBinding::inflate) {

    private  val viewModel by viewModel<RandomViewModel>()

    private var randomAdapter: RandomAdapter = RandomAdapter()

    override fun listeners() {

        binding.btnGenerate.setOnClickListener {

            observer()
            getData()

        }
    }

    override fun init() {
        binding.rvRecipes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = randomAdapter
        }
    }

    override fun observers() {

    }

    private fun observer() {

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getData()
                viewModel.recipeFlow.collect{
                    when(it) {
                        is ResponseHandler.Success<*> -> {
                            randomAdapter.submitList(it.result as MutableList<RandomRecipesInfo.RandomRecipe>?)
                        }
                        is ResponseHandler.Error -> {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                        }
                        is ResponseHandler.Loader -> {
                            Toast.makeText(context, "loading...", Toast.LENGTH_SHORT).show()
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
}