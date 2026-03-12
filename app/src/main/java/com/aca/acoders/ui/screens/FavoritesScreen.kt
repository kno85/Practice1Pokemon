package com.aca.acoders.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.domain.Pokemon
import com.aca.acoders.ui.screens.list.PokemonListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: PokemonListViewModel,
    onItemClick: (Pokemon) -> Unit,
    onNavigateBack: () -> Unit
) {
    val favorites by viewModel.favoritePokemon.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(favorites) { pokemon ->
                    PokemonListItem(
                        pokemon = pokemon,
                        isFavorite = true,
                        onFavoriteClick = { viewModel.toggleFavorite(pokemon) },
                        onItemClick = { onItemClick(pokemon) }
                    )
                }
            }
        }
    }
}
