package com.lukabaia.yummy.di

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
        HomeRepository(get(), get())
    }

    viewModel<HomeViewModel> {
        HomeViewModel(get())
    }

    single<DetailedRecipesApi> {
        RetrofitInstance.getDetailedRecipesApi()
    }

    single<DetailedRepository> {
        DetailedRepository(get(), get())
    }

    viewModel<DetailedViewModel> {
        DetailedViewModel(get())
    }

}