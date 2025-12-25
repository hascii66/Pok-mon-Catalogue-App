package com.example.pokmoncatalogueapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.pokmoncatalogueapp.constant.BASE_URL
import com.example.pokmoncatalogueapp.data.local.PokemonDatabase
import com.example.pokmoncatalogueapp.data.remote.PokeApiService
import com.example.pokmoncatalogueapp.data.repository.PokemonRepositoryImpl
import com.example.pokmoncatalogueapp.domain.usecase.GetPokemonListUseCase
import com.example.pokmoncatalogueapp.domain.usecase.SetRatingUseCase
import com.example.pokmoncatalogueapp.domain.usecase.ToggleBackpackUseCase
import com.example.pokmoncatalogueapp.domain.usecase.ToggleFavoriteUseCase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private var database: PokemonDatabase? = null
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
        database = PokemonDatabase.getDatabase(context)
    }

    private val api: PokeApiService by lazy {
        if (appContext == null) throw IllegalStateException("AppModule.initialize(context) must be called first!")

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(appContext!!).build())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

    val repository: PokemonRepositoryImpl by lazy {
        if (database == null) throw IllegalStateException("AppModule.initialize(context) must be called first!")
        PokemonRepositoryImpl(database!!.pokemonDao(), api)
    }

    val getPokemonListUseCase: GetPokemonListUseCase
        get() = GetPokemonListUseCase(repository)

    val toggleBackpackUseCase: ToggleBackpackUseCase
        get() = ToggleBackpackUseCase(repository)

    val toggleFavoriteUseCase: ToggleFavoriteUseCase
        get() = ToggleFavoriteUseCase(repository)

    val setRatingUseCase: SetRatingUseCase
        get() = SetRatingUseCase(repository)

    suspend fun initializeData() {
        if (database != null) {
            repository.fetchRemoteData()
        }
    }
}