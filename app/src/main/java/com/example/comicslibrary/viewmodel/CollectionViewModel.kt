package com.example.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.Movie
import com.example.comicslibrary.model.db.MovieEntity
import com.example.comicslibrary.model.repository.MovieDbRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repo: MovieDbRepoInterface
): ViewModel() {

    val currentMovie = MutableStateFlow<MovieEntity?>(null)
    val collection = MutableStateFlow<List<MovieEntity>>(emptyList())

    init {
        getCollection()
    }

    fun getCollection() {
        viewModelScope.launch {
            repo.getAllMovies().collect {
                collection.value = it
            }
        }
    }

    fun setCurrentMovieEntityWithId(movieId: Int) {
        viewModelScope.launch {
            repo.getMovieById(movieId).collect {
                currentMovie.value = it
            }
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            repo.insertMovie(MovieEntity.fromMovie(movie))
        }
    }


    fun delete(movie: Movie) {
        viewModelScope.launch {
            repo.deleteMovie(MovieEntity.fromMovie(movie))
        }
    }
}
