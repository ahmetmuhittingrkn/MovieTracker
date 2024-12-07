package com.example.filmtakip.data.repository

import com.example.filmtakip.data.entity.FavoriteMovie
import com.example.filmtakip.data.entity.Movie
import com.example.filmtakip.data.entity.MovieDetailResponse
import com.example.filmtakip.data.entity.MovieResponse
import com.example.filmtakip.retrofit.ApiService
import com.example.filmtakip.room.FavoriteMovieDao
import retrofit2.Response

class MovieRepository(private val apiService: ApiService,private val favoriteMovieDao: FavoriteMovieDao) {

    suspend fun getPopularMovies() = apiService.getPopularMovies()

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailResponse> {
        return apiService.getMovieDetails(movieId)
    }

    suspend fun addFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.addFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.removeFavorite(movie)
    }

    suspend fun getAllFavorites() : List<FavoriteMovie>{
        return favoriteMovieDao.getAllFavorites()
    }

    suspend fun isFavorite(id: Int): Boolean {
        return favoriteMovieDao.isFavorite(id)
    }

    suspend fun searchMovies(query:String) : Response<MovieResponse>{
        return apiService.searchMovies(query)
    }

}


