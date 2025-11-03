package com.aca.acoders


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.data.PokemonListViewModel
import com.aca.acoders.data.PokemonViewModelFactory
import com.aca.acoders.repository.PokemonRepository
import com.aca.acoders.ui.screens.PokemonDetailScreen
import com.aca.acoders.ui.screens.PokemonListScreen
import com.aca.acoders.ui.screens.detail.DetailScreen
import com.aca.acoders.ui.theme.Practice1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practice1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 1. Crea el NavController
                    val navController = rememberNavController()
                    val repository = PokemonRepository()
                    val viewModel: PokemonListViewModel = viewModel(
                        factory = PokemonViewModelFactory(repository)
                    )
                    // 2. Configura el NavHost con las rutas
                    NavHost(
                        navController = navController,
                        startDestination = "pokemonList", // Ruta inicial
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Ruta para la lista de Pokémon
                        composable("pokemonList") {
                            PokemonListScreen(
                                viewModel = viewModel,
                                onItemClick = { pokemon ->
                                    navController.navigate("detail/${pokemon.name}")
                                    selectedItem = pokemon.name
                                }                            )
                        }
                        composable(
                            route = "detail/{pokemonName}",
                            arguments = listOf(
                                navArgument("pokemonName") {
                                    // 2. Especifica el tipo de argumento.
                                    type = NavType.StringType
                                    // Opcional: puedes hacerlo nullable o darle un defaultValue
                                    nullable = false
                                }
                            )
                        ) { backStackEntry ->
                            // Aquí es donde recuperas el argumento:
                            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
                            val detailViewModel: PokemonDetailViewModel = viewModel(
                                factory = PokemonViewModelFactory(repository)
                            )
                            if (pokemonName != null) {
                                PokemonDetailScreen (
                                    viewModel = detailViewModel,
                                    pokemonName = pokemonName,
                                    onNavigateBack = { navController.popBackStack() })
                                }
                            }
                        }
                        }
                    }

                }
            }


    companion object {
        var selectedItem: String =""
    }
}
