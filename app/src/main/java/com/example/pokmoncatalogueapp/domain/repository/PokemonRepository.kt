package com.example.pokmoncatalogueapp.domain.repository

import com.example.pokmoncatalogueapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<List<Pokemon>>
    suspend fun fetchRemoteDataWithoutCheckRealm()
    suspend fun fetchRemoteData()
    suspend fun toggleBackpack(id: Int)
    suspend fun toggleFavorite(id: Int)
    suspend fun setRating(id: Int, rating: Int)
}