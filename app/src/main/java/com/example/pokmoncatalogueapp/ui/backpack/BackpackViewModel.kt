package com.example.pokmoncatalogueapp.ui.backpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokmoncatalogueapp.di.AppModule
import com.example.pokmoncatalogueapp.domain.model.Pokemon
import com.example.pokmoncatalogueapp.domain.usecase.FilterOption
import com.example.pokmoncatalogueapp.domain.usecase.SortOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BackpackViewModel : ViewModel() {
    private val getPokemonList = AppModule.getPokemonListUseCase
    private val toggleBackpack = AppModule.toggleBackpackUseCase
    private val toggleFavorite = AppModule.toggleFavoriteUseCase
    private val setRating = AppModule.setRatingUseCase

    val backpackList: StateFlow<List<Pokemon>> = getPokemonList(
        searchQuery = "",
        sortOption = SortOption.ID,
        filterOption = FilterOption.ALL,
        onlyBackpack = true
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onToggleBackpack(id: Int) = viewModelScope.launch { toggleBackpack(id) }
    fun onToggleFavorite(id: Int) = viewModelScope.launch { toggleFavorite(id) }
    fun onSetRating(id: Int, rating: Int) = viewModelScope.launch { setRating(id, rating) }
}