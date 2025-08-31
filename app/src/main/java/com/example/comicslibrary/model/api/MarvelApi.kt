package com.example.comicslibrary.model.api

import com.example.comicslibrary.model.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Call<CharacterDataWrapper>
}
