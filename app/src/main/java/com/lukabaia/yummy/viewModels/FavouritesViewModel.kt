package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.model.FavoriteRecipe
import com.lukabaia.yummy.repository.FavoritesRepository
import com.lukabaia.yummy.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FavouritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoritesFlow = MutableSharedFlow<ResponseHandler>()
    val favoritesFlow get() = _favoritesFlow.asSharedFlow()

    suspend fun removeRecipeByTitle(title: String) {
        favoritesRepository.removeRecipe(
            recipe = favoritesRepository.getRecipe(
                title = title
            )
        )
    }

    suspend fun getFavorites() {
        _favoritesFlow.emit(ResponseHandler.Loader(isLoading = true))
        try {
            _favoritesFlow.emit(
                ResponseHandler.Success(
                    result = favoritesRepository.getFavorites()
                )
            )
        } catch (e: Exception) {
            _favoritesFlow.emit(
                ResponseHandler.Error(
                    errorMessage = "Can't Get Data"
                )
            )
        }
        _favoritesFlow.emit(ResponseHandler.Loader(isLoading = false))
    }

}