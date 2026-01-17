package com.example.movielibrary.model.api

import com.example.movielibrary.model.MovieDiscoverResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchMovieAPI {

    @GET("search/movie")
    fun searchMovies(
        @Header("Authorization") authorization: String, // "Bearer YOUR_API_KEY"
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("region") region: String? = null,
        @Query("year") year: Int? = null,
    ): Call<MovieDiscoverResponse>
}
