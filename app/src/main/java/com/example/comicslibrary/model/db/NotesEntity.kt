package com.example.comicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.comicslibrary.model.Note
import com.example.comicslibrary.model.db.Constants.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val title: String,
    val text: String
) {
    companion object {
        fun fromNotes(note: Note): NotesEntity =
            NotesEntity(
                id = 0,
                movieId = note.movieId,
                title = note.title,
                text = note.text
            )
    }
}
