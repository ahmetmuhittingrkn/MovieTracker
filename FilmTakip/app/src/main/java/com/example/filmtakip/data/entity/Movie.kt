package com.example.filmtakip.data.entity

import com.google.gson.annotations.SerializedName

data class Movie( val id: Long,
                  val title: String,
                  @SerializedName("poster_path") val posterPath: String?,
                  @SerializedName("backdrop_path") val backdropPath: String?,
                  @SerializedName("release_date") val releaseDate: String?,
                  @SerializedName("vote_average") val voteAverage: Double,
                  val overview: String,
                  @SerializedName("genre_ids") val genreIds: List<Int>,
                  @SerializedName("popularity") val popularity: Double){
}