package com.aca.acoders.data

// /app/src/main/java/com/aca/acoders/data/PokemonListViewModelFactory.kt

import PokemonViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aca.acoders.repository.PokemonRepository

class PokemonViewModelFactory(
    private val pokemonRepository: PokemonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PokemonViewModel::class.java) -> {
                PokemonViewModel(pokemonRepository) as T
            }
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> {
                PokemonDetailViewModel(pokemonRepository) as T
            }
            // Add other ViewModel types as needed
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

