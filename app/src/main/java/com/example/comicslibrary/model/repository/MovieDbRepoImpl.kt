package com.example.comicslibrary.model.repository

import com.example.comicslibrary.model.db.MovieDao
import com.example.comicslibrary.model.db.MovieEntity
import com.example.comicslibrary.model.db.NoteDao
import com.example.comicslibrary.model.db.NotesEntity
import kotlinx.coroutines.flow.Flow

class MovieDbRepoImpl(
    private val movieDao: MovieDao,
    private val noteDao: NoteDao
) : MovieDbRepoInterface {
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

    override suspend fun getAllNotesForMovie(movieId: Int): Flow<List<NotesEntity?>> {
        return noteDao.getAllNotesForMovie(movieId)
    }

    override suspend fun getAllNotes(): Flow<List<NotesEntity?>> {
        return noteDao.getAllNote()
    }

    override suspend fun addNote(note: NotesEntity) {
        return noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NotesEntity) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        return noteDao.deleteNote(id)
    }

    override suspend fun deleteAllNotesForMovie(movie: MovieEntity) {
        return noteDao.deleteAllNotesForMovie(movie.id)
    }

}
