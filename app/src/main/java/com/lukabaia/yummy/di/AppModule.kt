package com.lukabaia.yummy.di

import androidx.room.Room
import com.lukabaia.yummy.adapter.FavoritesAdapter
import com.lukabaia.yummy.data.RecipesDatabase
import com.lukabaia.yummy.network.*
import com.lukabaia.yummy.repository.*
import com.lukabaia.yummy.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import kotlin.math.sin

val appModule = module {

    single<SearchRecipesApi> {
        RetrofitInstance.getSearchedRecipesApi()
    }

    single<HomeRepository> {
        HomeRepository(
            api = get(),
            application = get()
        )
    }

    viewModel<HomeViewModel> {
        HomeViewModel(
            homeRepository = get()
        )
    }

    single<DetailedRecipesApi> {
        RetrofitInstance.getDetailedRecipesApi()
    }

    single<DetailedRepository> {
        DetailedRepository(
            api = get(),
            database = get(),
            application = get()
        )
    }

    viewModel<DetailedViewModel> {
        DetailedViewModel(
            detailedRepository = get()
        )
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

    single<RandomRepository> {
        RandomRepository(
            api = get(),
            application = get()
        )
    }

    viewModel<RandomViewModel> {
        RandomViewModel(
            randomRepository = get()
        )
    }

    single<RecipesDatabase> {
        Room.databaseBuilder(
            get(),
            RecipesDatabase::class.java,
            "RecipesDB"
        ).build()
    }

    single<FavoritesRepository> {
        FavoritesRepository(
            database = get()
        )
    }

    viewModel<FavouritesViewModel> {
        FavouritesViewModel(
            favoritesRepository = get()
        )
    }

    single<SearchRepository> {
        SearchRepository(
            api = get(),
            application = get()
        )
    }

    viewModel<SearchViewModel> {
        SearchViewModel(
            repository = get()
        )
    }

    single<AssistantRepository> {
        AssistantRepository(
            suggestsApi = get(),
            assistantAnswerApi = get(),
            application = get()
        )
    }

    viewModel<AssistantViewModel> {
        AssistantViewModel(
            repository = get()
        )
    }

}