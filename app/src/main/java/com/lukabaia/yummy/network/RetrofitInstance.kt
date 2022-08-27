package com.lukabaia.yummy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.spoonacular.com/"

    private val instance by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Synchronized
    fun getRandomRecipesApi(): RandomRecipesApi = instance.create(RandomRecipesApi::class.java)

    @Synchronized
    fun getDetailedRecipesApi(): DetailedRecipesApi = instance.create(DetailedRecipesApi::class.java)

    @Synchronized
    fun getSearchedRecipesApi(): SearchRecipesApi = instance.create(SearchRecipesApi::class.java)

    @Synchronized
    fun getSuggestsApi(): SuggestsApi = instance.create(SuggestsApi::class.java)

    @Synchronized
    fun getAssistantAnswersApi(): AssistantAnswerApi = instance.create(AssistantAnswerApi::class.java)

}