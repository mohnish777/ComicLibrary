package com.example.comicslibrary.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.comicslibrary.model.db.Constants.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE WHERE movieId = :movieId ORDER BY id ASC")
    fun getAllNotesForMovie(movieId: Int): Flow<List<NotesEntity?>>

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY id ASC")
    fun getAllNote(): Flow<List<NotesEntity?>>

    @Insert(entity = NotesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotesEntity)

    @Update(entity = NotesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: NotesEntity)

    @Query("DELETE FROM $NOTE_TABLE WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("DELETE FROM $NOTE_TABLE WHERE movieId = :movieId")
    suspend fun deleteAllNotesForMovie(movieId: Int)

}
