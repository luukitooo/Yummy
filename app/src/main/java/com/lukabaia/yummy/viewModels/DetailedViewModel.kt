package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.FavoriteRecipe
import com.lukabaia.yummy.repository.DetailedRepository
import com.lukabaia.yummy.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DetailedViewModel(
    private val detailedRepository: DetailedRepository
) : ViewModel() {

    private val _detailsFlow = MutableSharedFlow<ResponseHandler>()
    val detailsFlow get() = _detailsFlow.asSharedFlow()

    private val _favoritesFlow = MutableSharedFlow<List<FavoriteRecipe>>()
    val favoritesFlow get() = _favoritesFlow.asSharedFlow()

    suspend fun getRecipeDetails(id: Int) {
        _detailsFlow.emit(ResponseHandler.Loader(isLoading = true))
        detailedRepository.getRecipeDetails(
            id = id
        ).also {
            if (it == null)
                _detailsFlow.emit(ResponseHandler.Error("No Data!"))
            else
                _detailsFlow.emit(ResponseHandler.Success(it))
        }
        _detailsFlow.emit(ResponseHandler.Loader(isLoading = false))
    }

    suspend fun getFavorites() {
        _favoritesFlow.emit(
            detailedRepository.getFavorites()
        )
    }

    suspend fun addRecipe(recipe: FavoriteRecipe) {
        detailedRepository.addRecipe(recipe)
    }

    suspend fun removeRecipe(recipe: FavoriteRecipe) {
        detailedRepository.removeRecipe(recipe)
    }

}