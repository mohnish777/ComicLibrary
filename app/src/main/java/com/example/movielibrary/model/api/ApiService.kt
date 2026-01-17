package com.example.movielibrary.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // TMDB does not require query-string auth when using Bearer; keeping method for compatibility if needed

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SearchMovieAPI by lazy {
        getRetrofit().create(SearchMovieAPI::class.java)
    }


}
