package com.example.comicslibrary.model.api

import com.example.comicslibrary.model.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RatedMovieAPI {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Header("Authorization") authorization: String, // "Bearer YOUR_API_KEY"
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("primary_release_year") releaseYear: Int? = null,
        @Query("with_genres") genres: String? = null,
        @Query("vote_average.gte") minRating: Double? = null
    ): Call<MovieDiscoverResponse>
}
