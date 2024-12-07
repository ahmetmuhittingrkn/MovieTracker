package com.example.filmtakip.retrofit

import com.example.filmtakip.BuildConfig
import com.example.filmtakip.data.entity.MovieDetailResponse
import com.example.filmtakip.data.entity.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String= BuildConfig.API_KEY
    ): Response<MovieDetailResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query:String,
        @Query("api_key") apiKey: String=BuildConfig.API_KEY
    ) : Response<MovieResponse>
}