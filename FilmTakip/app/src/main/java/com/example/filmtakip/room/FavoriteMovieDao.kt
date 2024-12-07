package com.example.filmtakip.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmtakip.data.entity.FavoriteMovie

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(movie:FavoriteMovie)

    @Delete
    suspend fun removeFavorite(movie:FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavorites() : List<FavoriteMovie>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

}