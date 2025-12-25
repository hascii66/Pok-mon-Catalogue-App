package com.example.pokmoncatalogueapp.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String> = emptyList(),
    val inBackpack: Boolean = false,
    val isFavorite: Boolean = false,
    val rating: Int = 0
)