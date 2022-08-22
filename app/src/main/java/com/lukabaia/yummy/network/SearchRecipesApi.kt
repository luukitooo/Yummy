package com.lukabaia.yummy.network

import com.lukabaia.yummy.model.SearchedRecipesInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchRecipesApi {

    @GET("recipes/complexSearch")
    @Headers("Content-Type: application/json")
    suspend fun getDetailedRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("diet") diet: String,
        @Query("type") type: String,
        @Query("titleMatch") titleMatch: String,
        @Query("maxReadyTime") maxReadyTime: Int,
        @Query("minCarbs") minCarbs: Int,
        @Query("maxCarbs") maxCarbs: Int,
        @Query("minProtein") minProtein: Int,
        @Query("maxProtein") maxProtein: Int,
        @Query("minCalories") minCalories: Int,
        @Query("maxCalories") maxCalories: Int,
        @Query("number") number: Int,
    ): Response<SearchedRecipesInfo>

    @GET("recipes/complexSearch")
    @Headers("Content-Type: application/json")
    suspend fun getPreviewRecipes(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String,
        @Query("number") number: Int,
    ): Response<SearchedRecipesInfo>
}