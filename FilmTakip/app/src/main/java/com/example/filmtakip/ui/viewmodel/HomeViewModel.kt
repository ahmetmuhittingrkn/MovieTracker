package com.example.filmtakip.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmtakip.BuildConfig
import com.example.filmtakip.data.entity.Movie
import com.example.filmtakip.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: MovieRepository) : ViewModel(){

    private val _movies= MutableLiveData<List<Movie>>()
    val movies :  LiveData<List<Movie>> get() = _movies

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _errorMessage= MutableLiveData<String?>()
    val errorMessage : LiveData<String?> get() = _errorMessage

    fun fetchPopularMovies() {
        _isLoading.value=true
        Log.d("API_KEY", "API Key: ${BuildConfig.API_KEY}")
        viewModelScope.launch {
            try {
                val response=repository.getPopularMovies()

                if(response.isSuccessful) {
                    _movies.value = response.body()?.results?.map { it.copy(id = it.id) } ?: emptyList()
                } else {
                    _errorMessage.value="Veri çekilemedi: ${response.message()}"
                }

            } catch ( e:Exception) {
                _errorMessage.value="Hata: ${e.localizedMessage}"
            } finally {
                _isLoading.value=false
            }
        }
    }
}


