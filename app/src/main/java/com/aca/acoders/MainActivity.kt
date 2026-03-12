package com.aca.acoders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.ui.screens.FavoritesScreen
import com.aca.acoders.ui.screens.PokemonDetailScreen
import com.aca.acoders.ui.screens.PokemonListScreen
import com.aca.acoders.ui.theme.Practice1Theme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Practice1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "pokemonList",
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.statusBars)
                            .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.navigationBars)
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {

                        composable("pokemonList") {
                            val listViewModel: PokemonListViewModel = koinViewModel()
                            PokemonListScreen(
                                viewModel = listViewModel,
                                onItemClick = { pokemon ->
                                    selectedItem = pokemon.name
                                    navController.navigate("detail/${pokemon.name}")
                                },
                                onNavigateToFavorites = {
                                    navController.navigate("favorites")
                                }
                            )
                        }

                        composable("favorites") {
                            val viewModel: PokemonListViewModel = koinViewModel()
                            FavoritesScreen(
                                viewModel = viewModel,
                                onItemClick = { pokemon ->
                                    selectedItem = pokemon.name
                                    navController.navigate("detail/${pokemon.name}")
                                },
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            route = "detail/{pokemonName}",
                            arguments = listOf(
                                navArgument("pokemonName") {
                                    type = NavType.StringType
                                    nullable = false
                                }
                            )
                        ) { entry ->
                            val pokemonName = entry.arguments?.getString("pokemonName")
                            val detailViewModel: PokemonDetailViewModel = koinViewModel()

                            if (pokemonName != null) {
                                PokemonDetailScreen(
                                    viewModel = detailViewModel,
                                    pokemonName = pokemonName,
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        var selectedItem: String = ""
    }
}
