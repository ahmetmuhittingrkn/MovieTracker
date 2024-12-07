package com.example.filmtakip.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmtakip.data.entity.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteMovieDao() : FavoriteMovieDao
}