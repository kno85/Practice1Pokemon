package com.aca.acoders.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aca.acoders.domain.Pokemon

@Entity(tableName = "pokemon_list")
data class PokemonListEntity(
    @PrimaryKey val name: String,
    val url: String
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val name: String,
    val url: String
)

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey val name: String,
    val jsonContent: String
)

fun PokemonListEntity.toDomain() = Pokemon(name = name, url = url)
fun Pokemon.toListEntity() = PokemonListEntity(name = name, url = url)

fun FavoriteEntity.toDomain() = Pokemon(name = name, url = url)
fun Pokemon.toFavoriteEntity() = FavoriteEntity(name = name, url = url)
