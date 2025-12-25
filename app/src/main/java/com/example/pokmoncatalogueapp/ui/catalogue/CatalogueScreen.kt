package com.example.pokmoncatalogueapp.ui.catalogue

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokmoncatalogueapp.R
import com.example.pokmoncatalogueapp.constant.DELAY_1_5_M
import com.example.pokmoncatalogueapp.domain.usecase.FilterOption
import com.example.pokmoncatalogueapp.domain.usecase.SortOption
import com.example.pokmoncatalogueapp.ui.common.PokemonCard
import com.example.pokmoncatalogueapp.ui.theme.PokemonRed
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogueScreen() {
    val viewModel: CatalogueViewModel = viewModel()
    val pokemonList by viewModel.pokemonList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentSort by viewModel.sortOption.collectAsState()
    val currentFilter by viewModel.filterOption.collectAsState()

    var showFilterSheet by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    if (isRefreshing) {
        LaunchedEffect(true) {
            viewModel.refreshData().join()
            delay(DELAY_1_5_M)
            isRefreshing = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.pokedex_gen_one),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokemonRed,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { showFilterSheet = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { isRefreshing = true },
            state = pullToRefreshState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(dimensionResource(R.dimen.size_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_l)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_l)),
                modifier = Modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(2) }) {
                    TextField(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchChange,
                        placeholder = { Text(stringResource(R.string.search_pokemon)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(R.dimen.size_l))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.size_l))),
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                if (pokemonList.isEmpty() && searchQuery.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier.height(dimensionResource(R.dimen.default_icon_size)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    items(pokemonList, key = { it.id }) { pokemon ->
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

    if (showFilterSheet) {
        AlertDialog(
            onDismissRequest = { showFilterSheet = false },
            title = { Text(stringResource(R.string.options)) },
            text = {
                Column {
                    Text(
                        stringResource(R.string.sort_by),
                        fontWeight = FontWeight.Bold,
                        color = PokemonRed
                    )
                    Row(Modifier.horizontalScroll(rememberScrollState())) {
                        SortOption.entries.forEach { option ->
                            FilterChip(
                                selected = currentSort == option,
                                onClick = { viewModel.onSortChange(option) },
                                label = { Text(option.name) },
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.size_s))
                            )
                        }
                    }
                    Spacer(Modifier.height(dimensionResource(R.dimen.size_xxl)))
                    Text(
                        stringResource(R.string.filter_type),
                        fontWeight = FontWeight.Bold,
                        color = PokemonRed
                    )
                    Row(Modifier.horizontalScroll(rememberScrollState())) {
                        FilterOption.entries.forEach { option ->
                            FilterChip(
                                selected = currentFilter == option,
                                onClick = { viewModel.onFilterChange(option) },
                                label = { Text(option.name) },
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.size_s))
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFilterSheet = false }) {
                    Text(
                        stringResource(R.string.action_done)
                    )
                }
            }
        )
    }
}
