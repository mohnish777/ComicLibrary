package com.example.movielibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movielibrary.model.db.Constants.MOVIE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY id ASC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity?>

    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Update(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: MovieEntity)

    @Delete(entity = MovieEntity::class)
    suspend fun deleteMovie(movie: MovieEntity)

}
