package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.network.DetailedRecipesInfo
import com.lukabaia.yummy.repository.DetailedRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DetailedViewModel(
    private val detailedRepository: DetailedRepository
) : ViewModel() {

    private val _detailsFlow = MutableSharedFlow<DetailedRecipesInfo?>()
    val detailsFlow get() = _detailsFlow.asSharedFlow()

    suspend fun getRecipeDetails(id: Int) {
        detailedRepository.getRecipeDetails(
            id = id
        ).also {
            _detailsFlow.emit(it)
        }
    }

}