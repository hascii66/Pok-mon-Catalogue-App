package com.example.pokmoncatalogueapp.domain.usecase

import com.example.pokmoncatalogueapp.domain.model.Pokemon
import com.example.pokmoncatalogueapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class SortOption { ID, NAME_A_TO_Z, NAME_Z_TO_A, TYPE }
enum class FilterOption { ALL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, POISON, BUG, NORMAL }

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(
        searchQuery: String,
        sortOption: SortOption,
        filterOption: FilterOption,
        onlyBackpack: Boolean = false
    ): Flow<List<Pokemon>> {
        return repository.getPokemonList().map { list ->
            var result = list

            if (onlyBackpack) {
                result = result.filter { it.inBackpack }
            }

            if (searchQuery.isNotEmpty()) {
                val q = searchQuery.lowercase()
                result = result.filter { it.name.lowercase().contains(q) }
            }

            if (filterOption != FilterOption.ALL) {
                val typeName = filterOption.name.lowercase()
                result = result.filter { p -> p.types.any { it == typeName } }
            }

            when (sortOption) {
                SortOption.ID -> result.sortedBy { it.id }
                SortOption.NAME_A_TO_Z -> result.sortedBy { it.name }
                SortOption.NAME_Z_TO_A -> result.sortedByDescending { it.name }
                SortOption.TYPE -> result.sortedBy { it.types.firstOrNull() ?: "z" }
            }
        }
    }
}

class ToggleBackpackUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int) = repository.toggleBackpack(id)
}

class ToggleFavoriteUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int) = repository.toggleFavorite(id)
}

class SetRatingUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int, rating: Int) = repository.setRating(id, rating)
}