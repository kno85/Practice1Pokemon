package com.aca.acoders.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aca.acoders.data.PokemonDetailViewModel
import com.aca.acoders.domain.PokemonDetail
import com.aca.acoders.domain.detail.Stat
import java.util.Locale

// Paleta dinámica basada en tipo
private fun colorForType(typeName: String): Color = when (typeName.lowercase()) {
    "grass" -> Color(0xFF4CAF50)
    "poison" -> Color(0xFF7B1FA2)
    "fire" -> Color(0xFFEF5350)
    "water" -> Color(0xFF42A5F5)
    "electric" -> Color(0xFFFFCA28)
    "flying" -> Color(0xFF81D4FA)
    "bug" -> Color(0xFF9CCC65)
    "normal" -> Color(0xFFB0BEC5)
    else -> Color(0xFF607D8B)
}
private object StatRowDefaults {
    const val MAX_STAT_VALUE = 255f
    val STAT_NAME_WIDTH = 120.dp
    val STAT_VALUE_WIDTH = 36.dp
    val INDICATOR_HEIGHT = 10.dp
    val INDICATOR_CORNER_RADIUS = 8.dp
    val HORIZONTAL_SPACING = 8.dp
}

@Composable
fun DetailScreen(
    pokemon: PokemonDetail?, // El objeto PokemonDetail puede ser nulo mientras carga
    onBack: () -> Unit
) {
    // CORRECCIÓN: Comprobamos si el pokemon es nulo. Si lo es, mostramos una pantalla de carga.
    if (pokemon == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        PokemonDetailContent(pokemon = pokemon, onBack = onBack)        // Si no es nulo, mostramos el contenido con los datos del pokemon.
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailContent(
    pokemon: PokemonDetail, // Aquí el pokemon ya no puede ser nulo
    onBack: () -> Unit
) {
    // MEJORA: `remember` con clave. El color solo se recalcula si el objeto `pokemon` cambia.
    val primaryColor = remember(pokemon) {
        pokemon.types.firstOrNull()?.let { colorForType(it.type.name) } ?: Color(0xFF607D8B)
    }
    val gradientBrush = remember(primaryColor) {
        Brush.verticalGradient(listOf(primaryColor.copy(alpha = 0.95f), Color.White))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "#${pokemon.id} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                        maxLines = 1,
                        color = Color.White, // Mejor contraste sobre el color primario
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header card con imagen y datos básicos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .background(gradientBrush)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pokemon.sprites.other.officialArtwork.frontDefault, // CORRECCIÓN: URL correcta desde el modelo
                            contentDescription = "${pokemon.name} artwork",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = pokemon.name.replaceFirstChar { it.uppercase() },
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row {
                                // CORRECCIÓN: Usar el modelo de datos correcto `PokemonDetail.Type`
                                pokemon.types.forEach { pokemonType ->
                                    ChipType(type = pokemonType.type.name)
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Altura: ${pokemon.height / 10.0} m · Peso: ${pokemon.weight / 10.0} kg",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estadísticas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    pokemon.stats.forEach { stat ->
                        StatRow(stat = stat, accent = primaryColor)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Abilities Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Habilidades", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    pokemon.abilities.forEachIndexed { index, abilityInfo ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(text = abilityInfo.ability.name.replaceFirstChar { it.uppercase() }, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.weight(1f))
                            if (abilityInfo.isHidden) {
                                Text(text = "Oculta", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                        if (index < pokemon.abilities.lastIndex) Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipType(type: String) {
    val color = colorForType(type)
    Surface(
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.95f)
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

// Objeto para almacenar las dimensiones y valores constantes de StatRow


// Función de extensión para formatear los nombres de las estadísticas
private fun String.formatStatName(): String {
    return this.replace('-', ' ').replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

@Composable
private fun StatRow(stat: Stat, accent: Color) {
    // Calcula el progreso una vez y asegúrate de que esté entre 0 y 1
    val progress = (stat.baseStat.toFloat() / StatRowDefaults.MAX_STAT_VALUE).coerceIn(0f, 1f)

    // MEJORA: Se elimina el `Column` exterior que era redundante
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            // MEJORA: Se usa la función de extensión para formatear el nombre
            text = stat.stat.name.formatStatName(),
            modifier = Modifier.width(StatRowDefaults.STAT_NAME_WIDTH)
        )
        Spacer(modifier = Modifier.width(StatRowDefaults.HORIZONTAL_SPACING))
        LinearProgressIndicator(
            // MEJORA: Se pasa el valor `Float` directamente, ya no se necesita la lambda
            progress = progress,
            modifier = Modifier
                .height(StatRowDefaults.INDICATOR_HEIGHT)
                .weight(1f)
                .clip(RoundedCornerShape(StatRowDefaults.INDICATOR_CORNER_RADIUS)),
            trackColor = Color.LightGray.copy(alpha = 0.3f),
            color = accent
        )
        Spacer(modifier = Modifier.width(StatRowDefaults.HORIZONTAL_SPACING))
        Text(
            text = "${stat.baseStat}",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(StatRowDefaults.STAT_VALUE_WIDTH),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
