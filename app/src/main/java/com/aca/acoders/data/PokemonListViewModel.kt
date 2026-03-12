package com.aca.acoders.data

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonList = mutableStateListOf<Pokemon>()
    
    var searchQuery by mutableStateOf("")
        private set

    val filteredPokemonList by derivedStateOf {
        if (searchQuery.isEmpty()) {
            _pokemonList
        } else {
            _pokemonList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

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

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun loadNextPage() {
        if (isLoading.value || isEndReached || searchQuery.isNotEmpty()) return

        viewModelScope.launch {
            isLoading.value = true
            val results = repository.getPokemonList(currentPage, 20).pokemons

            if (results.isEmpty()) {
                isEndReached = true
            } else {
                _pokemonList.addAll(results)
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
