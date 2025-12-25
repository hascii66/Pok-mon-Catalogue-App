package com.example.pokmoncatalogueapp.data.remote

import com.example.pokmoncatalogueapp.data.remote.dto.PokemonDetailResponse
import com.example.pokmoncatalogueapp.data.remote.dto.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 200
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailResponse
}