package com.aca.acoders.repository

import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.PokemonList
import com.aca.acoders.network.PokemonService

class PokemonRepository(private val pokemonService: PokemonService) : PokemonService {

    override suspend fun getPokemonList(page: Int, pageSize: Int): PokemonList {
        val response = pokemonService.getPokemonList(offset = page * pageSize, limit = pageSize)
        return response ?: PokemonList(0, "", "", emptyList())
    }

    override suspend fun getPokemon(pokemonName: String): PokemonDetail? {
        return pokemonService.getPokemon(pokemonName)
    }
}
