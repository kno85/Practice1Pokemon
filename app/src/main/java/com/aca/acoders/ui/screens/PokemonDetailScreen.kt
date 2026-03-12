package com.aca.acoders.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.ui.screens.detail.DetailScreen

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
    pokemonName: String,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(key1 = pokemonName) {
        viewModel.loadPokemon(pokemonName)
    }

    val pokemon: PokemonDetail? by viewModel.pokemon.observeAsState(initial = null)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val error: String? by viewModel.error.observeAsState(initial = null)

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error ?: "Ocurrió un error inesperado",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateBack) {
                    Text("Volver atrás")
                }
            }
        } else if (pokemon != null) {
            DetailScreen(
                pokemon = pokemon,
                onBack = onNavigateBack
            )
        }
    }
}
