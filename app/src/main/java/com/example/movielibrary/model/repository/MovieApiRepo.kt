package com.example.movielibrary.model.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.movielibrary.BuildConfig
import com.example.movielibrary.model.Movie
import com.example.movielibrary.model.MovieDiscoverResponse
import com.example.movielibrary.model.api.NetworkResult
import com.example.movielibrary.model.api.SearchMovieAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieApiRepo(private val api: SearchMovieAPI) {
    val movies = MutableStateFlow<NetworkResult<MovieDiscoverResponse>>(NetworkResult.Initial())

    val movieDetailById: MutableState<Movie?> = mutableStateOf(null)
    private var currentSearchJob: Job? = null

    fun clearResults() {
        println("DEBUG - clearResults() called, setting to Initial state")
        currentSearchJob?.cancel()
        movies.value = NetworkResult.Initial()
    }

    fun query(query: String) {
        // Cancel any ongoing search
        currentSearchJob?.cancel()

        movies.value = NetworkResult.Loading()
        // NOTE: RatedMovieAPI expects an Authorization header (Bearer token)
        currentSearchJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val authorization = "Bearer ${BuildConfig.MOVIE_ACCESS_TOKEN}"
                val call = api.searchMovies(
                    authorization = authorization,
                    language = "en-US",
                    query = query,
                    page = 1,
                    includeAdult = false,
                    region = null,
                    year = 2025
                )
                val response: Response<MovieDiscoverResponse> = call.execute()
                if (response.isSuccessful) {
                    val data: MovieDiscoverResponse? = response.body()
                    data?.let { wrapper ->
                        println("Movies page: ${wrapper.page} / totalPages=${wrapper.totalPages} totalResults=${wrapper.totalResults}")
                        wrapper.results.forEach { movie ->
                            println("Movie: ${movie.title}")
                            println("Overview: ${movie.overview}")
                            println("Vote Avg: ${movie.voteAverage}")
                            println("---")
                        }
                    }
                    movies.value = NetworkResult.Success(data = data as MovieDiscoverResponse)

                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                    println("Headers: ${response.headers()}")
                    movies.value = NetworkResult.Error(response.message())
                }
            } catch (t: Throwable) {
                println("Network error: ${t.message}")
                movies.value = NetworkResult.Error(message = t.message.toString())
            }
        }
    }

    fun getMovieDetailById(id: Int?) {
        id?.let {
            movieDetailById.value = movies.value.data?.results?.firstOrNull {
                it.id == id
            }
        }
    }
}
