package com.example.comicslibrary.model.repository

import com.example.comicslibrary.model.db.MovieEntity
import com.example.comicslibrary.model.db.NotesEntity
import kotlinx.coroutines.flow.Flow

interface MovieDbRepoInterface {

    suspend fun getAllMovies(): Flow<List<MovieEntity>>

    suspend fun getMovieById(id: Int): Flow<MovieEntity?>

    suspend fun insertMovie(movie: MovieEntity)

    suspend fun updateMovie(movie: MovieEntity)

    suspend fun deleteMovie(movie: MovieEntity)

    // Notes

    suspend fun getAllNotesForMovie(movieId: Int): Flow<List<NotesEntity?>>

    suspend fun getAllNotes(): Flow<List<NotesEntity?>>

    suspend fun addNote(note: NotesEntity)

    suspend fun updateNote(note: NotesEntity)

    suspend fun deleteNote(id: Int)

    suspend fun deleteAllNotesForMovie(movie: MovieEntity)
}
