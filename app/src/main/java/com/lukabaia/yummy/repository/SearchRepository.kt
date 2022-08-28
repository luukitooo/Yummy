package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import com.lukabaia.yummy.network.recipes.SearchRecipesApi

class SearchRepository(
    private val api: SearchRecipesApi,
    private val application: Application
) {

    suspend fun getRecipesByTitle(title: String): SearchedRecipesInfo? {
        val response = api.getRecipes(
            apiKey = application.getString(R.string.api_key),
            titleMatch = title
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

}