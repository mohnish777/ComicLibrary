package com.example.comicslibrary.model.api

import com.example.comicslibrary.BuildConfig
import com.example.comicslibrary.generateHash
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    // Helper function to get authentication parameters
    fun getAuthParams(): Map<String, String> {
        val ts = (System.currentTimeMillis() / 1000).toString() // Use seconds instead of milliseconds
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY
        val hash = generateHash(
            ts = ts,
            privateKey = apiSecret,
            publicKey = apiKey
        )

        return mapOf(
            "apikey" to apiKey,
            "ts" to ts,
            "hash" to hash
        )
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RatedMovieAPI by lazy {
        getRetrofit().create(RatedMovieAPI::class.java)
    }


}
