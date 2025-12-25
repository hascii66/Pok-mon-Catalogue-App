package com.example.pokmoncatalogueapp.data.repository

import com.example.pokmoncatalogueapp.data.local.PokemonDao
import com.example.pokmoncatalogueapp.data.local.PokemonEntity
import com.example.pokmoncatalogueapp.data.remote.PokeApiService
import com.example.pokmoncatalogueapp.data.remote.dto.PokemonListEntryDto
import com.example.pokmoncatalogueapp.domain.model.Pokemon
import com.example.pokmoncatalogueapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Locale

class PokemonRepositoryImpl(
    private val dao: PokemonDao,
    private val api: PokeApiService
) : PokemonRepository {

    override fun getPokemonList(): Flow<List<Pokemon>> {
        return dao.getAllPokemon().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fetchRemoteDataWithoutCheckRealm() {
        fetchAndSaveData()
    }

    override suspend fun fetchRemoteData() {
        withContext(Dispatchers.IO) {
            if (dao.getCount() > 0) return@withContext

            fetchAndSaveData()
        }
    }

    private suspend fun fetchAndSaveData() = withContext(Dispatchers.IO) {
        try {
            val response = api.getPokemonList()

            val entities = response.results.map { entry ->
                val id = entry.url.extractIdFromUrl()
                val formattedName = entry.name.formatPokemonName()
                val typesString = fetchPokemonTypes(id)
                val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

                PokemonEntity(
                    id = id,
                    name = formattedName,
                    imageUrl = imageUrl,
                    types = typesString
                )
            }

            dao.insertAll(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateBackpackState(id: Int, inBackpack: Boolean) = dao.updateBackpack(id, inBackpack)
    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean) = dao.updateFavorite(id, isFavorite)

    override suspend fun toggleBackpack(id: Int) {
        val currentList = dao.getAllPokemon().first()
        val item = currentList.find { it.id == id }
        if (item != null) {
            updateBackpackState(id, !item.inBackpack)
        }
    }

    override suspend fun toggleFavorite(id: Int) {
        val currentList = dao.getAllPokemon().first()
        val item = currentList.find { it.id == id }
        if (item != null) {
            updateFavoriteState(id, !item.isFavorite)
        }
    }

    override suspend fun setRating(id: Int, rating: Int) = dao.updateRating(id, rating)

    private fun String.extractIdFromUrl(): Int {
        return this.split("/").last { it.isNotEmpty() }.toInt()
    }

    private fun String.formatPokemonName(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }
    }

    private suspend fun fetchPokemonTypes(id: Int): String {
        return try {
            val details = api.getPokemonDetail(id)
            details.types.joinToString(",") { it.type.name }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}