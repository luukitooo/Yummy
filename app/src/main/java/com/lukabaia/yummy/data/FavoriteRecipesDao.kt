package com.lukabaia.yummy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lukabaia.yummy.model.FavoriteRecipe

@Dao
interface FavoriteRecipesDao {

    @Insert
    suspend fun addRecipe(recipe: FavoriteRecipe)

    @Delete
    suspend fun removeRecipe(recipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes ORDER BY id DESC")
    suspend fun getAllRecipes(): List<FavoriteRecipe>

    @Query("SELECT * FROM favorite_recipes WHERE title = :title")
    suspend fun getRecipe(title: String): FavoriteRecipe

}