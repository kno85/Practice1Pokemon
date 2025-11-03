package com.aca.acoders.ui.screens

// /app/src/main/java/com/aca/acoders/ui/screens/list/PokemonListScreen.kt
// Modificación en el archivo donde llamas a `viewModel()`
// Por ejemplo, en /app/src/main/java/com/aca/acoders/ui/screens/list/PokemonListScreen.kt

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.ui.screens.list.PokemonListItem


@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onItemClick: (Pokemon) -> Unit // Recibe la acción de clic
) {
    val pokemonList: List<Pokemon> by viewModel.pokemonList.observeAsState(initial = emptyList())

    LazyColumn {
        items(pokemonList) { pokemon ->
            PokemonListItem(
                pokemon = pokemon,
                onItemClick = { onItemClick(pokemon) }
            )
        }
    }
}