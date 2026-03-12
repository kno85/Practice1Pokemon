package com.aca.acoders.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {
    var pokemonList = mutableStateListOf<Pokemon>()
    private var currentPage = 0
    var isEndReached = false
    var isLoading = mutableStateOf(false)

    val favoritePokemon: StateFlow<List<Pokemon>> = repository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading.value || isEndReached) return

        viewModelScope.launch {
            isLoading.value = true
            val results = repository.getPokemonList(currentPage, 20).pokemons

            if (results.isEmpty()) {
                isEndReached = true
            } else {
                pokemonList.addAll(results)
                currentPage++
            }
            isLoading.value = false
        }
    }

    fun toggleFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            if (repository.isFavorite(pokemon.name)) {
                repository.removeFavorite(pokemon)
            } else {
                repository.addFavorite(pokemon)
            }
        }
    }

    fun isFavorite(pokemonName: String, favorites: List<Pokemon>): Boolean {
        return favorites.any { it.name == pokemonName }
    }
}
