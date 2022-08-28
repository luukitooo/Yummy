package com.lukabaia.yummy.network.assistant

import com.lukabaia.yummy.model.assistant.AssistantAnswer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AssistantAnswerApi {

    @GET("food/converse")
    suspend fun getAssistantAnswer(
        @Query("apiKey") apiKey: String,
        @Query("text") text: String
    ): Response<AssistantAnswer>

}