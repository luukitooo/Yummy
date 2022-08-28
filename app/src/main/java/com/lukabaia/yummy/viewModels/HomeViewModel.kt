package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.repository.HomeRepository
import com.lukabaia.yummy.utils.RecipeTypes
import com.lukabaia.yummy.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _recipesFlow = MutableSharedFlow<ResponseHandler>()
    val recipesFlow = _recipesFlow.asSharedFlow()

    private val _categoryFlow = MutableStateFlow(RecipeTypes.MAIN_COURSE)
    val categoryFlow = _categoryFlow.asStateFlow()

    suspend fun getSearchResult(type: String, number: Int) {
        _recipesFlow.emit(ResponseHandler.Loader(isLoading = true))
        homeRepository.getSearchResult(
            type = type,
            number = number
        ).also {
            if (it == null)
                _recipesFlow.emit(ResponseHandler.Error("No Data!"))
            else
                _recipesFlow.emit(ResponseHandler.Success(it))
        }
        _recipesFlow.emit(ResponseHandler.Loader(isLoading = false))
    }

    suspend fun setType(type: RecipeTypes) {
        _categoryFlow.emit(type)
    }
}