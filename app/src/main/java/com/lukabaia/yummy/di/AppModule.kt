package com.lukabaia.yummy.di

import androidx.room.Room
import com.lukabaia.yummy.data.RecipesDatabase
import com.lukabaia.yummy.network.*
import com.lukabaia.yummy.network.assistant.AssistantAnswerApi
import com.lukabaia.yummy.network.assistant.SuggestsApi
import com.lukabaia.yummy.network.recipes.DetailedRecipesApi
import com.lukabaia.yummy.network.recipes.RandomRecipesApi
import com.lukabaia.yummy.network.recipes.SearchRecipesApi
import com.lukabaia.yummy.repository.*
import com.lukabaia.yummy.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Repositories

    single<SearchRecipesApi> {
        RetrofitInstance.getSearchedRecipesApi()
    }

    single<HomeRepository> {
        HomeRepository(
            api = get(),
            application = get()
        )
    }

    single<DetailedRepository> {
        DetailedRepository(
            api = get(),
            database = get(),
            application = get()
        )
    }

    single<RandomRepository> {
        RandomRepository(
            api = get(),
            application = get()
        )
    }

    single<FavoritesRepository> {
        FavoritesRepository(
            database = get()
        )
    }

    single<SearchRepository> {
        SearchRepository(
            api = get(),
            application = get()
        )
    }

    single<AssistantRepository> {
        AssistantRepository(
            suggestsApi = get(),
            assistantAnswerApi = get(),
            application = get()
        )
    }

    // ViewModels

    viewModel<HomeViewModel> {
        HomeViewModel(
            homeRepository = get()
        )
    }

    viewModel<DetailedViewModel> {
        DetailedViewModel(
            detailedRepository = get()
        )
    }

    viewModel<RandomViewModel> {
        RandomViewModel(
            randomRepository = get()
        )
    }

    viewModel<FavouritesViewModel> {
        FavouritesViewModel(
            favoritesRepository = get()
        )
    }

    viewModel<SearchViewModel> {
        SearchViewModel(
            repository = get()
        )
    }

    viewModel<AssistantViewModel> {
        AssistantViewModel(
            repository = get()
        )
    }

    // APIs

    single<DetailedRecipesApi> {
        RetrofitInstance.getDetailedRecipesApi()
    }

    single<RandomRecipesApi> {
        RetrofitInstance.getRandomRecipesApi()
    }

    single<SuggestsApi> {
        RetrofitInstance.getSuggestsApi()
    }

    single<AssistantAnswerApi> {
        RetrofitInstance.getAssistantAnswersApi()
    }

    // Database

    single<RecipesDatabase> {
        Room.databaseBuilder(
            get(),
            RecipesDatabase::class.java,
            "RecipesDB"
        ).build()
    }

}