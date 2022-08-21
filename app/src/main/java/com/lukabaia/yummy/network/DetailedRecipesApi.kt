package com.lukabaia.yummy.network

import com.lukabaia.yummy.model.DetailedRecipesInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DetailedRecipesApi {

    @GET("recipes/{id}/information")
    @Headers("Content-Type: application/json")
    suspend fun getDetailedRecipes(
        @Query("apiKey") apiKey: String,
        @Query("id") id: Int,
        ): Response<DetailedRecipesInfo>





}