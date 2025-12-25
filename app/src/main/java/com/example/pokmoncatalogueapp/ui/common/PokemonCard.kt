package com.example.pokmoncatalogueapp.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokmoncatalogueapp.R
import com.example.pokmoncatalogueapp.domain.model.Pokemon
import com.example.pokmoncatalogueapp.ui.theme.PokemonBlue
import com.example.pokmoncatalogueapp.ui.theme.getTypeColor

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onBackpackToggle: (Int) -> Unit,
    onFavoriteToggle: (Int) -> Unit,
    onRate: (Int, Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val mainTypeColor = pokemon.types.firstOrNull()?.let { getTypeColor(it) } ?: Color.Gray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                mainTypeColor.copy(alpha = 0.6f),
                                Color.White
                            )
                        )
                    )
            ) {
                Surface(
                    Modifier.padding(8.dp),
                    color = Color.Black.copy(0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "#${pokemon.id.toString().padStart(3, '0')}",
                        color = Color.White,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                IconButton(
                    onClick = { onFavoriteToggle(pokemon.id) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        if (pokemon.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        "Fav",
                        tint = if (pokemon.isFavorite) Color.Red else Color.White
                    )
                }
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }
            Column(Modifier.padding(8.dp)) {
                Text(
                    pokemon.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    pokemon.types.forEach { TypeChip(it); Spacer(Modifier.width(4.dp)) }
                }
                AnimatedVisibility(isExpanded) {
                    Column(Modifier.padding(top = 8.dp)) {
                        Divider()
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            (1..5).forEach { star ->
                                Icon(
                                    if (star <= pokemon.rating) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                                    null,
                                    tint = Color(0xFFFFC107),
                                    modifier = Modifier.clickable { onRate(pokemon.id, star) })
                            }
                        }
                        Button(
                            onClick = { onBackpackToggle(pokemon.id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = if (pokemon.inBackpack) Color.Gray else PokemonBlue)
                        ) {
                            Text(if (pokemon.inBackpack) stringResource(R.string.remove_from_bag) else stringResource(
                                R.string.add_to_bag
                            ))
                        }
                    }
                }
            }
        }
    }
}