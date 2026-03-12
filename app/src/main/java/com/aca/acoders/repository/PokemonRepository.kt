package com.aca.acoders.repository

import com.aca.acoders.data.local.dao.PokemonDao
import com.aca.acoders.data.local.entities.PokemonDetailEntity
import com.aca.acoders.data.local.entities.toDomain
import com.aca.acoders.data.local.entities.toFavoriteEntity
import com.aca.acoders.data.local.entities.toListEntity
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.PokemonList
import com.aca.acoders.network.PokemonService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val gson: Gson
) {

    suspend fun getPokemonList(page: Int, pageSize: Int): PokemonList {
        return try {
            val response = pokemonService.getPokemonList(offset = page * pageSize, limit = pageSize)
            if (response != null) {
                // Cache the list
                if (page == 0) {
                    pokemonDao.clearPokemonList()
                }
                pokemonDao.insertPokemonList(response.pokemons.map { it.toListEntity() })
                response
            } else {
                getCachedPokemonList()
            }
        } catch (e: Exception) {
            getCachedPokemonList()
        }
    }

    private suspend fun getCachedPokemonList(): PokemonList {
        val cached = pokemonDao.getPokemonList()
        return PokemonList(
            count = cached.size,
            next = null,
            previous = null,
            pokemons = cached.map { it.toDomain() }
        )
    }

    suspend fun getPokemon(pokemonName: String): PokemonDetail? {
        return try {
            val response = pokemonService.getPokemon(pokemonName)
            if (response != null) {
                // Cache detail
                val entity = PokemonDetailEntity(pokemonName, gson.toJson(response))
                pokemonDao.insertPokemonDetail(entity)
                response
            } else {
                getCachedPokemonDetail(pokemonName)
            }
        } catch (e: Exception) {
            getCachedPokemonDetail(pokemonName)
        }
    }

    private suspend fun getCachedPokemonDetail(name: String): PokemonDetail? {
        val cached = pokemonDao.getPokemonDetail(name)
        return cached?.let {
            gson.fromJson(it.jsonContent, PokemonDetail::class.java)
        }
    }

    // Favorites Logic
    fun getFavorites(): Flow<List<Pokemon>> {
        return pokemonDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun addFavorite(pokemon: Pokemon) {
        pokemonDao.insertFavorite(pokemon.toFavoriteEntity())
    }

    suspend fun removeFavorite(pokemon: Pokemon) {
        pokemonDao.deleteFavorite(pokemon.toFavoriteEntity())
    }

    suspend fun isFavorite(name: String): Boolean {
        return pokemonDao.isFavorite(name)
    }
}
