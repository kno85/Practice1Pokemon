package com.aca.acoders.di

import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.data.PokemonViewModel
import com.aca.acoders.network.PokemonService
import com.aca.acoders.repository.PokemonRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Proveer Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Proveer el Servicio de API
    single<PokemonService> { get<Retrofit>().create(PokemonService::class.java) }

    // Proveer el Repositorio
    single { PokemonRepository(get()) }

    // Proveer los ViewModels
    viewModel { PokemonViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
}
