package com.lukabaia.yummy.network

import com.lukabaia.yummy.model.assistant.SuggestsInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SuggestsApi {

    @GET("food/converse/suggest")
    suspend fun getConversationSuggests(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int
    ): Response<SuggestsInfo>

}