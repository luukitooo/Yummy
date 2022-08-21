package com.lukabaia.yummy.network

import com.lukabaia.yummy.model.RandomRecipesInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomRecipesApi {

    @GET("recipes/random")
    @Headers("Content-Type: application/json")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Response<RandomRecipesInfo>

}