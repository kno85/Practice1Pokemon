package com.aca.acoders.network

import com.aca.acoders.domain.Pokemon
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Archivo: /Users/antoniocanoalmagro/AndroidStudioProjects/Practice1/app/src/main/java/com/aca/acoders/network/PokemonService.kt

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonList?

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemon(
        @Path("pokemonName") pokemonName: String
    ): PokemonDetail?
}
