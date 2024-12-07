package com.example.filmtakip.data.entity

data class MovieDetailResponse( val id: Long,
                                val title: String,
                                val overview: String,
                                val release_date: String,
                                val vote_average: Double,
                                val poster_path: String?,
                                val backdrop_path: String?,
                                val runtime: Int?,
                                val genres: List<Genre>) {
}