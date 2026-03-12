package com.aca.acoders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aca.acoders.MainActivity
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemon = MutableLiveData<PokemonDetail?>()
    val pokemon: LiveData<PokemonDetail?> get() = _pokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        val pokemonName = MainActivity.selectedItem
        if (pokemonName.isNotEmpty()) {
            loadPokemon(pokemonName)
        }
    }

    fun loadPokemon(pokemonName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result: PokemonDetail? = pokemonRepository.getPokemon(pokemonName)
                if (result != null) {
                    _pokemon.postValue(result)
                } else {
                    _error.postValue("No se pudo cargar la información del Pokémon. Revisa tu conexión.")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
