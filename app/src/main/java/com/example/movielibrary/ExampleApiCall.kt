package com.example.movielibrary

import com.example.movielibrary.model.MovieDiscoverResponse
import com.example.movielibrary.model.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

// Updated to match the new Retrofit structure (RatedMovieAPI)
class ExampleApiCall {

    // Kept the method name so existing callers (e.g., LibraryScreen) continue to work
    fun fetchCharacters() {
        // NOTE: RatedMovieAPI expects an Authorization header (Bearer token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authorization = "Bearer ${BuildConfig.MOVIE_ACCESS_TOKEN}"
                val call = ApiService.api.searchMovies(
                    authorization = authorization,
                    language = "en-US",
                    query = "Harry Potter",
                    page = 1,
                    includeAdult = false,
                    region = null,
                    year = null
                )
                val response: Response<MovieDiscoverResponse> = call.execute()
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let { wrapper ->
                        println("Movies page: ${wrapper.page} / totalPages=${wrapper.totalPages} totalResults=${wrapper.totalResults}")
                        wrapper.results.forEach { movie ->
                            println("Movie: ${movie.title}")
                            println("Overview: ${movie.overview}")
                            println("Vote Avg: ${movie.voteAverage}")
                            println("---")
                        }
                    }
                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                    println("Headers: ${response.headers()}")
                }
            } catch (t: Throwable) {
                println("Network error: ${t.message}")
            }
        }
    }
}

