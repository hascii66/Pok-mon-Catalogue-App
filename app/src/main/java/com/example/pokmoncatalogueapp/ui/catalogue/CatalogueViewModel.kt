package com.example.pokmoncatalogueapp.ui.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokmoncatalogueapp.di.AppModule
import com.example.pokmoncatalogueapp.domain.model.Pokemon
import com.example.pokmoncatalogueapp.domain.usecase.FilterOption
import com.example.pokmoncatalogueapp.domain.usecase.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatalogueViewModel : ViewModel() {
    private val repository = AppModule.repository
    private val getPokemonList = AppModule.getPokemonListUseCase
    private val toggleBackpack = AppModule.toggleBackpackUseCase
    private val toggleFavorite = AppModule.toggleFavoriteUseCase
    private val setRating = AppModule.setRatingUseCase

    private val _searchQuery = MutableStateFlow("")
    private val _sortOption = MutableStateFlow(SortOption.ID)
    private val _filterOption = MutableStateFlow(FilterOption.ALL)

    val pokemonList: StateFlow<List<Pokemon>> = combine(
        _searchQuery,
        _sortOption,
        _filterOption,
        flowOf(Unit)
    ) { query, sort, filter, _ ->
        Triple(query, sort, filter)
    }.flatMapLatest { (query, sort, filter) ->
        getPokemonList(query, sort, filter, onlyBackpack = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchQuery = _searchQuery.asStateFlow()
    val sortOption = _sortOption.asStateFlow()
    val filterOption = _filterOption.asStateFlow()

    init {
        viewModelScope.launch { AppModule.initializeData() }
    }

    fun refreshData() = viewModelScope.launch {
        repository.fetchRemoteDataWithoutCheckRealm()
    }

    fun onSearchChange(q: String) {
        _searchQuery.value = q
    }

    fun onSortChange(s: SortOption) {
        _sortOption.value = s
    }

    fun onFilterChange(f: FilterOption) {
        _filterOption.value = f
    }

    fun onToggleBackpack(id: Int) = viewModelScope.launch { toggleBackpack(id) }
    fun onToggleFavorite(id: Int) = viewModelScope.launch { toggleFavorite(id) }
    fun onSetRating(id: Int, rating: Int) = viewModelScope.launch { setRating(id, rating) }
}