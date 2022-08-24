package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.repository.RandomRepository
import com.lukabaia.yummy.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class RandomViewModel(private val randomRepository: RandomRepository) : ViewModel() {

    private val _recipeFlow = MutableSharedFlow<ResponseHandler>()
    val recipeFlow = _recipeFlow.asSharedFlow()

    suspend fun getRandomRecipe(number: Int) {
        _recipeFlow.emit(ResponseHandler.Loader(isLoading = true))
        randomRepository.getRandomRecipe(number = number).also {
            if (it == null)
                _recipeFlow.emit(ResponseHandler.Error("No Data!"))
            else
                _recipeFlow.emit(ResponseHandler.Success(it.recipes))
        }
        _recipeFlow.emit(ResponseHandler.Loader(isLoading = false))
    }

}