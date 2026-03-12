package com.aca.acoders.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aca.acoders.data.local.dao.PokemonDao
import com.aca.acoders.data.local.entities.FavoriteEntity
import com.aca.acoders.data.local.entities.PokemonDetailEntity
import com.aca.acoders.data.local.entities.PokemonListEntity

@Database(
    entities = [
        FavoriteEntity::class,
        PokemonListEntity::class,
        PokemonDetailEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
