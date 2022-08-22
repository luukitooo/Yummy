package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.SearchedRecipesInfo
import com.lukabaia.yummy.repository.HomeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _recipesFlow = MutableSharedFlow<SearchedRecipesInfo?>()
    val recipesFlow = _recipesFlow.asSharedFlow()

    suspend fun getSearchResult(type: String, number: Int) {
        homeRepository.getSearchResult(
            type = type,
            number = number
        ).also {
            _recipesFlow.emit(it)
        }
    }

}