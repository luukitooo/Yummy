package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.data.RecipesDatabase
import com.lukabaia.yummy.model.FavoriteRecipe
import com.lukabaia.yummy.model.network.DetailedRecipesInfo
import com.lukabaia.yummy.network.recipes.DetailedRecipesApi

class DetailedRepository(
    private val api: DetailedRecipesApi,
    private val database: RecipesDatabase,
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

    suspend fun getFavorites(): List<FavoriteRecipe> {
        return database.getFavoriteRecipesDao().getAllRecipes()
    }

    suspend fun addRecipe(recipe: FavoriteRecipe) {
        database.getFavoriteRecipesDao().addRecipe(recipe)
    }

    suspend fun removeRecipe(recipe: FavoriteRecipe) {
        database.getFavoriteRecipesDao().removeRecipe(recipe)
    }

}