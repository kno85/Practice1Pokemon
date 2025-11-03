package com.aca.acoders.data

// Improved Code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aca.acoders.MainActivity
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.launch

// 1. Suggestion: Use Dependency Injection
class PokemonDetailViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    // 2. Suggestion: Expose an immutable LiveData object
    private val _pokemon = MutableLiveData<PokemonDetail?>()
    val pokemon: LiveData<PokemonDetail?> get() = _pokemon
    init {
        var pokemonName: String= MainActivity.selectedItem
        loadPokemon(pokemonName)
    }
    fun loadPokemon(pokemonName: String) {
        // Use viewModelScope to launch a coroutine that is automatically
        // cancelled when the ViewModel is cleared.
        viewModelScope.launch {
            // Assuming getPokemonList is now a suspend function for asynchronous work
            val result: PokemonDetail? = pokemonRepository.getPokemon(pokemonName)
            _pokemon.postValue(result)
        }
    }
}
