package com.aca.acoders.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.ui.screens.list.PokemonListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onItemClick: (Pokemon) -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val listState = rememberLazyListState()
    val pokemonList = viewModel.pokemonList
    val favorites by viewModel.favoritePokemon.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokemon List") },
                actions = {
                    IconButton(onClick = onNavigateToFavorites) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(paddingValues)
        ) {
            itemsIndexed(pokemonList) { index, pokemon ->
                val isFavorite = viewModel.isFavorite(pokemon.name, favorites)

                PokemonListItem(
                    pokemon = pokemon,
                    isFavorite = isFavorite,
                    onFavoriteClick = { viewModel.toggleFavorite(pokemon) },
                    onItemClick = { onItemClick(pokemon) }
                )

                if (index >= pokemonList.size - 1 && !viewModel.isLoading.value && !viewModel.isEndReached) {
                    LaunchedEffect(key1 = true) {
                        viewModel.loadNextPage()
                    }
                }
            }

            if (viewModel.isLoading.value) {
                item {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                }
            }
        }
    }
}
