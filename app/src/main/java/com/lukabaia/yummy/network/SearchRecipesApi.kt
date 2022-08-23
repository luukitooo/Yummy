package com.lukabaia.yummy.network

import com.lukabaia.yummy.model.network.SearchedRecipesInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchRecipesApi {

    @GET("recipes/complexSearch")
    @Headers("Content-Type: application/json")
    suspend fun getDetailedRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String? = null,
        @Query("diet") diet: String? = null,
        @Query("type") type: String? = null,
        @Query("titleMatch") titleMatch: String? = null,
        @Query("maxReadyTime") maxReadyTime: Int? = null,
        @Query("minCarbs") minCarbs: Int? = null,
        @Query("maxCarbs") maxCarbs: Int? = null,
        @Query("minProtein") minProtein: Int? = null,
        @Query("maxProtein") maxProtein: Int? = null,
        @Query("minCalories") minCalories: Int? = null,
        @Query("maxCalories") maxCalories: Int? = null,
        @Query("number") number: Int? = null,
    ): Response<SearchedRecipesInfo>

}