package com.example.comicslibrary.model.repository

import com.example.comicslibrary.model.db.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDbRepoInterface {

    suspend fun getAllMovies(): Flow<List<MovieEntity>>

    suspend fun getMovieById(id: Int): Flow<MovieEntity>

    suspend fun insertMovie(movie: MovieEntity)

    suspend fun updateMovie(movie: MovieEntity)

    suspend fun deleteMovie(movie: MovieEntity)
}
