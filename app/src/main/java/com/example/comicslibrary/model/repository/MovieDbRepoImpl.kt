package com.example.comicslibrary.model.repository

import com.example.comicslibrary.model.db.MovieDao
import com.example.comicslibrary.model.db.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieDbRepoImpl(private val movieDao: MovieDao): MovieDbRepoInterface {
    override suspend fun getAllMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }

    override suspend fun getMovieById(id: Int): Flow<MovieEntity?> {
        return movieDao.getMovieById(id)
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        return movieDao.insertMovie(movie)
    }

    override suspend fun updateMovie(movie: MovieEntity) {
        return movieDao.updateMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        return movieDao.deleteMovie(movie)
    }

}
