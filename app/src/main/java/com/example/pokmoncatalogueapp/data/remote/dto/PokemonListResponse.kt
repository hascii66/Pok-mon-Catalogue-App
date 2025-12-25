package com.example.pokmoncatalogueapp.data.remote.dto

data class PokemonListResponse(
    val results: List<PokemonListEntryDto>
)

data class PokemonListEntryDto(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<TypeSlotDto>
)

data class TypeSlotDto(
    val slot: Int,
    val type: TypeInfoDto
)

data class TypeInfoDto(
    val name: String
)