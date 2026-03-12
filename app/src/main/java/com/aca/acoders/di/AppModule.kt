package com.aca.acoders.di

import androidx.room.Room
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.data.local.PokemonDatabase
import com.aca.acoders.network.PokemonService
import com.aca.acoders.repository.PokemonRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Proveer Gson
    single { Gson() }

    // Proveer Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Proveer Room Database
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "pokemon_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Proveer DAO
    single { get<PokemonDatabase>().pokemonDao() }

    // Proveer el Servicio de API
    single<PokemonService> { get<Retrofit>().create(PokemonService::class.java) }

    // Proveer el Repositorio
    single { PokemonRepository(get(), get(), get()) }

    // Proveer los ViewModels
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
}
