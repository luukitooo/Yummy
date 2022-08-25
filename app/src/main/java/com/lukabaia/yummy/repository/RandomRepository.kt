package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.model.network.RandomRecipesInfo
import com.lukabaia.yummy.network.RandomRecipesApi

class RandomRepository(
    private val api: RandomRecipesApi,
    private val application: Application
) {

    suspend fun getRandomRecipe(number: Int): RandomRecipesInfo? {
        val response = api.getRandomRecipes(
            apiKey = application.resources.getString(R.string.api_key),
            number = number
        )
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}