package com.aca.acoders.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.ui.screens.detail.DetailScreen

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
    pokemonName: String, // El nombre del pokémon a cargar, pasado desde la navegación
    onNavigateBack: () -> Unit // Lambda para la acción de "volver atrás"
) {
    // 1. Efecto para cargar los detalles del Pokémon una sola vez cuando la pantalla aparece
    LaunchedEffect(key1 = pokemonName) {
        viewModel.loadPokemon(pokemonName)
    }

    // 2. Se observa el estado del Pokémon desde el ViewModel
    val pokemon: PokemonDetail? by viewModel.pokemon.observeAsState(initial = null)

    // 3. Se llama directamente a DetailScreen, que ya gestiona su propio scroll.
    //    Se elimina el LazyColumn que era innecesario y se pasa la lambda onNavigateBack.
    DetailScreen(
        pokemon = pokemon,
        onBack = onNavigateBack // Se pasa la acción de navegación recibida
    )
}
