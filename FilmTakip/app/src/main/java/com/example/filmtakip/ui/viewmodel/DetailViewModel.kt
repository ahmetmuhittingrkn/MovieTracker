package com.example.filmtakip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmtakip.data.entity.FavoriteMovie
import com.example.filmtakip.data.entity.MovieDetailResponse
import com.example.filmtakip.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetailResponse>()
    val movieDetails: LiveData<MovieDetailResponse> get() = _movieDetails

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchMovieDetails(movieId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getMovieDetails(movieId.toInt())
                if (response.isSuccessful) {
                    response.body()?.let { movieDetail ->
                        _movieDetails.value = movieDetail
                    } ?: run {
                        _errorMessage.value = "Movie details are empty."
                    }
                } else {
                    _errorMessage.value = "Failed to fetch details: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching movie details: ${e.message}"
                Log.e("DetailViewModel", "Exception: ", e)
            }
        }
    }

    private fun checkIsFavorite(movieId: Int) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(movieId)
        }
    }

    fun addFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.addFavorite(movie)
            _isFavorite.value = true
        }
    }

    fun removeFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
            _isFavorite.value = false
        }
    }
}

