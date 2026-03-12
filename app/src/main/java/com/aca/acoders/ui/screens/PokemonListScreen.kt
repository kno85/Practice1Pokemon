package com.aca.acoders.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val pokemonList = viewModel.filteredPokemonList
    val favorites by viewModel.favoritePokemon.collectAsState()
    val searchQuery = viewModel.searchQuery

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Pokemon List") },
                    actions = {
                        IconButton(onClick = onNavigateToFavorites) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        }
                    }
                )
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) }
                )
            }
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

                // Solo cargar más si no estamos filtrando
                if (searchQuery.isEmpty() && index >= pokemonList.size - 1 && !viewModel.isLoading.value && !viewModel.isEndReached) {
                    LaunchedEffect(key1 = true) {
                        viewModel.loadNextPage()
                    }
                }
            }

            if (viewModel.isLoading.value && searchQuery.isEmpty()) {
                item {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Buscar Pokémon...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Borrar")
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
