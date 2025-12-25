package com.example.pokmoncatalogueapp.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokmoncatalogueapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String,
    val inBackpack: Boolean = false,
    val isFavorite: Boolean = false,
    val rating: Int = 0
) {
    fun toDomain(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            imageUrl = imageUrl,
            types = if (types.isEmpty()) emptyList() else types.split(","),
            inBackpack = inBackpack,
            isFavorite = isFavorite,
            rating = rating
        )
    }
}

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonEntity>)

    @Query("UPDATE pokemon_table SET inBackpack = :inBackpack WHERE id = :id")
    suspend fun updateBackpack(id: Int, inBackpack: Boolean)

    @Query("UPDATE pokemon_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE pokemon_table SET rating = :rating WHERE id = :id")
    suspend fun updateRating(id: Int, rating: Int)

    @Query("SELECT COUNT(*) FROM pokemon_table")
    suspend fun getCount(): Int
}

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}