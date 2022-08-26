package com.lukabaia.yummy.viewModels

import androidx.lifecycle.ViewModel
import com.lukabaia.yummy.repository.SearchRepository
import com.lukabaia.yummy.utils.ResponseHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _recipesFlow = MutableSharedFlow<ResponseHandler>()
    val recipesFlow get() = _recipesFlow.asSharedFlow()

    suspend fun getRecipesByTitle(title: String) {
        _recipesFlow.emit(ResponseHandler.Loader(isLoading = true))
        repository.getRecipesByTitle(
            title = title
        ).also {
            if (it != null) {
                _recipesFlow.emit(ResponseHandler.Success(it))
            } else {
                _recipesFlow.emit(
                    ResponseHandler.Error(
                        errorMessage = "Can't get data!"
                    )
                )
            }
            _recipesFlow.emit(ResponseHandler.Loader(isLoading = false))
        }
    }

}