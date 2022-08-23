package com.lukabaia.yummy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukabaia.yummy.model.FavoriteRecipe

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun getFavoriteRecipesDao(): FavoriteRecipesDao

}