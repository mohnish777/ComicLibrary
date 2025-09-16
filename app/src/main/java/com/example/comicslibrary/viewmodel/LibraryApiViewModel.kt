package com.example.comicslibrary.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.repository.MovieApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryApiViewModel @Inject constructor(
    private val repo: MovieApiRepo
): ViewModel() {
    val movies = repo.movies
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)

    init {
        retrieveMovies()
    }

    @OptIn(FlowPreview::class)
    private fun retrieveMovies() {
        viewModelScope.launch(Dispatchers.IO){
            queryInput.receiveAsFlow()
                .debounce { 1000L }
                .collect { query ->
                    if (validateQuery(query)) {
                        repo.query(query)
                    } else {
                        // If query becomes invalid (< 2 chars), clear results
                        repo.clearResults()
                    }
                }

        }

    }

    private fun validateQuery(it: String): Boolean {
        return it.length >= 2
    }

    fun onQueryUpdate(query: String) {
        viewModelScope.launch {
            queryText.value = query
            queryInput.trySend(query)
        }
    }

}
