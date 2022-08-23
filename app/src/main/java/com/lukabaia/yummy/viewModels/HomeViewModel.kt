package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.SearchedRecipesInfo
import com.lukabaia.yummy.repository.HomeRepository
import com.lukabaia.yummy.utils.RecipeTypes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _recipesFlow = MutableSharedFlow<SearchedRecipesInfo?>()
    val recipesFlow = _recipesFlow.asSharedFlow()

    private val _categoryFlow = MutableStateFlow(RecipeTypes.MAIN_COURSE)
    val categoryFlow = _categoryFlow.asStateFlow()

    suspend fun getSearchResult(type: String, number: Int) {
        homeRepository.getSearchResult(
            type = type,
            number = number
        ).also {
            _recipesFlow.emit(it)
        }
    }

    suspend fun setType(type: RecipeTypes) {
        _categoryFlow.emit(type)
    }

}