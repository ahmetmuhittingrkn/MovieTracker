package com.example.filmtakip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmtakip.data.entity.Movie
import com.example.filmtakip.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {

    private val _searchResults= MutableLiveData<List<Movie>>()
    val searchResults : LiveData<List<Movie>> get() = _searchResults

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(query)
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    _searchResults.value = results
                } else {
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }
}