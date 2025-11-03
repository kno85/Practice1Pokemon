package com.aca.acoders.network

import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(
    ): PokemonList?

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemon(
        @Path("pokemonName") pokemonName: String
    ): PokemonDetail?
}