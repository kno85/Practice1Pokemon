package com.aca.acoders.data

// Improved Code
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.launch

// 1. Suggestion: Use Dependency Injection
class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    // 2. Suggestion: Expose an immutable LiveData object
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    // You can call this during ViewModel initialization to load data automatically
    init {
        loadPokemonList()
    }

    // 3. Suggestion: Use coroutines for background operations and improve naming
    fun loadPokemonList() {
        // Use viewModelScope to launch a coroutine that is automatically
        // cancelled when the ViewModel is cleared.
        viewModelScope.launch {
            // Assuming getPokemonList is now a suspend function for asynchronous work
            val result = pokemonRepository.getPokemonList()
            _pokemonList.postValue(result?.pokemons)
        }
    }
}
