package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import com.lukabaia.yummy.network.recipes.SearchRecipesApi

class HomeRepository(
    private val api: SearchRecipesApi,
    private val application: Application
) {

    suspend fun getSearchResult(type: String, number: Int): SearchedRecipesInfo? {
        val response = api.getRecipes(
            apiKey = application.resources.getString(R.string.api_key),
            type = type,
            number = number
        )
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

}