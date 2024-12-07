package com.example.filmtakip.ui.viewmodel

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
class FavoritesViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {

    private val _favorites= MutableLiveData<List<FavoriteMovie>>()
    val favorites : LiveData<List<FavoriteMovie>> get() = _favorites

    fun loadFavorites () {
        viewModelScope.launch {
            _favorites.postValue(repository.getAllFavorites())
        }
    }

    fun removeFavorite(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
            loadFavorites()
        }
    }

}


