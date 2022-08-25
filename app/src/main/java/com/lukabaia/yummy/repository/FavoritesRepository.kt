package com.lukabaia.yummy.repository

import com.lukabaia.yummy.data.RecipesDatabase
import com.lukabaia.yummy.model.FavoriteRecipe

class FavoritesRepository(
    private val database: RecipesDatabase
) {

    suspend fun getFavorites(): List<FavoriteRecipe> {
        return database.getFavoriteRecipesDao().getAllRecipes()
    }

    suspend fun getRecipe(title: String): FavoriteRecipe {
        return database.getFavoriteRecipesDao().getRecipe(title)
    }

    suspend fun removeRecipe(recipe: FavoriteRecipe) {
        database.getFavoriteRecipesDao().removeRecipe(recipe)
    }

}