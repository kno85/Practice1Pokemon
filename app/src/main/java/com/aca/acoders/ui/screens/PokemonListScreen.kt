package com.aca.acoders.ui.screens

import PokemonViewModel
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.ui.screens.list.PokemonListItem


@Composable
fun PokemonListScreen(viewModel: PokemonViewModel, onItemClick: (Pokemon) -> Unit) {
    val listState = rememberLazyListState()
    val pokemonList = viewModel.pokemonList

    LazyColumn(state = listState) {
        itemsIndexed(pokemonList) { index, pokemon ->
            // Renderiza tu item de Pokémon
            PokemonListItem(pokemon, onItemClick = {
               onItemClick(pokemon)
            })

            // Lógica de Scroll Infinito:
            // Si llegamos al último elemento de la lista actual, cargamos más.
            if (index >= pokemonList.size - 1 && !viewModel.isLoading.value && !viewModel.isEndReached) {
                LaunchedEffect(key1 = true) {
                    viewModel.loadNextPage()
                }
            }
        }

        // Indicador de carga al final
        if (viewModel.isLoading.value) {
            item {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
            }
        }
    }
}
