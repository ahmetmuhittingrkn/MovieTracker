package com.example.filmtakip.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_movies")
data class FavoriteMovie(@PrimaryKey val id:Int,
                         val title:String,
                         val posterPath:String?,
                         val releaseDate:String?,
                         val voteAverage:Double) {
}