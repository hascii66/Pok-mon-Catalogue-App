package com.example.pokmoncatalogueapp.ui.backpack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokmoncatalogueapp.R
import com.example.pokmoncatalogueapp.ui.common.PokemonCard
import com.example.pokmoncatalogueapp.ui.theme.PokemonBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackpackScreen() {
    val viewModel: BackpackViewModel = viewModel()
    val backpackList by viewModel.backpackList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.my_backpack),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokemonBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (backpackList.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { Text(stringResource(R.string.backpack_empty)) }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(dimensionResource(R.dimen.size_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_l)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_l)),
                modifier = Modifier.padding(padding)
            ) {
                items(backpackList, key = { it.id }) { pokemon ->
                    PokemonCard(
                        pokemon = pokemon,
                        onBackpackToggle = viewModel::onToggleBackpack,
                        onFavoriteToggle = viewModel::onToggleFavorite,
                        onRate = viewModel::onSetRating
                    )
                }
            }
        }
    }
}