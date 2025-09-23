package com.example.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.Movie
import com.example.comicslibrary.model.db.MovieEntity
import com.example.comicslibrary.model.db.NotesEntity
import com.example.comicslibrary.model.repository.MovieDbRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repo: MovieDbRepoInterface
): ViewModel() {

    val currentMovie = MutableStateFlow<MovieEntity?>(null)
    val collection = MutableStateFlow<List<MovieEntity>>(emptyList())

    val notes = MutableStateFlow<List<NotesEntity>>(emptyList())
    private var currentMovieJob: Job? = null // ✅ Track current job

    init {
        getCollection()
        getNotes()
    }

    fun getCollection() {
        viewModelScope.launch {
            repo.getAllMovies().collect {
                collection.value = it
            }
        }
    }

    fun setCurrentMovieEntityWithId(movieId: Int) {
        currentMovieJob?.cancel() // ✅ Cancel previous job
        currentMovieJob = viewModelScope.launch {
            repo.getMovieById(movieId).collect { movieEntity ->
                currentMovie.value = movieEntity
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
            repo.deleteAllNotesForMovie(MovieEntity.fromMovie(movie))
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            repo.getAllNotes().collect {
                notes.value = it.filterNotNull()
            }
        }
    }

    fun addNote(note: NotesEntity) {
        viewModelScope.launch {
            repo.addNote(note)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repo.deleteNote(id)
        }
    }

}
