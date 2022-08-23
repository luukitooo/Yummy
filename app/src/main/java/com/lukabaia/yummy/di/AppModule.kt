package com.lukabaia.yummy.di

import androidx.room.Room
import com.lukabaia.yummy.data.RecipesDatabase
import com.lukabaia.yummy.network.DetailedRecipesApi
import com.lukabaia.yummy.network.RetrofitInstance
import com.lukabaia.yummy.network.SearchRecipesApi
import com.lukabaia.yummy.repository.DetailedRepository
import com.lukabaia.yummy.repository.HomeRepository
import com.lukabaia.yummy.viewModels.DetailedViewModel
import com.lukabaia.yummy.viewModels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
            application = get()
        )
    }

    viewModel<DetailedViewModel> {
        DetailedViewModel(
            detailedRepository = get()
        )
    }

    single<RecipesDatabase> {
        Room.databaseBuilder(
            get(),
            RecipesDatabase::class.java,
            "RecipesDB"
        ).build()
    }

}