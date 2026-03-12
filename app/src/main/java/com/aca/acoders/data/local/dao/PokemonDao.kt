package com.aca.acoders.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aca.acoders.data.local.entities.FavoriteEntity
import com.aca.acoders.data.local.entities.PokemonDetailEntity
import com.aca.acoders.data.local.entities.PokemonListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    // Favorites
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pokemon: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(pokemon: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean

    // Pokemon List (Cache)
    @Query("SELECT * FROM pokemon_list")
    suspend fun getPokemonList(): List<PokemonListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonListEntity>)

    @Query("DELETE FROM pokemon_list")
    suspend fun clearPokemonList()

    // Pokemon Detail (Cache)
    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    suspend fun getPokemonDetail(name: String): PokemonDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonDetail(pokemonDetail: PokemonDetailEntity)
}
