package com.lukabaia.yummy.repository

import android.app.Application
import com.lukabaia.yummy.R
import com.lukabaia.yummy.model.assistant.AssistantAnswer
import com.lukabaia.yummy.model.assistant.SuggestsInfo
import com.lukabaia.yummy.network.assistant.AssistantAnswerApi
import com.lukabaia.yummy.network.assistant.SuggestsApi

class AssistantRepository(
    private val suggestsApi: SuggestsApi,
    private val assistantAnswerApi: AssistantAnswerApi,
    private val application: Application
) {

    suspend fun getSuggests(query: String): SuggestsInfo? {
        val response = suggestsApi.getConversationSuggests(
            apiKey = application.getString(R.string.api_key),
            query = query,
            number = 5
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

    suspend fun getAssistantAnswer(text: String): AssistantAnswer? {
        val response = assistantAnswerApi.getAssistantAnswer(
            apiKey = application.getString(R.string.api_key),
            text = text
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }

}