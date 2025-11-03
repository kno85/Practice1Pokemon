package com.aca.acoders.repository

import RetrofitClient
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.PokemonList
import com.aca.acoders.network.PokemonService


class PokemonRepository: PokemonService{
    val  pokemonService = RetrofitClient.instance.create(PokemonService::class.java)

    override suspend fun getPokemonList(): PokemonList? {
        return pokemonService.getPokemonList()
    }

    override suspend fun getPokemon(pokemonName: String): PokemonDetail? {
        return pokemonService.getPokemon(pokemonName)    }
}

