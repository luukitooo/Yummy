package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.model.DetailedRecipesInfo
import com.lukabaia.yummy.network.DetailedRecipesApi

class DetailedRepository(
    private val api: DetailedRecipesApi,
    private val application: Application
) {

    suspend fun getRecipeDetails(id: Int): DetailedRecipesInfo? {
        val response = api.getDetailedRecipes(
            apiKey = application.getString(R.string.api_key),
            id = id
        )
        if (response.isSuccessful)
            return response.body()
        return null
    }

}