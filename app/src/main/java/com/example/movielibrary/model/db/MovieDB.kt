package com.example.movielibrary.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [MovieEntity::class, NotesEntity::class], version = 2, exportSchema = false)
abstract class MovieDB: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun noteDao(): NoteDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create new table with all columns
                db.execSQL("""
                    CREATE TABLE movie_table_new (
                        id INTEGER PRIMARY KEY NOT NULL,
                        title TEXT NOT NULL,
                        originalTitle TEXT NOT NULL,
                        overview TEXT NOT NULL,
                        posterPath TEXT,
                        backdropPath TEXT,
                        releaseDate TEXT NOT NULL,
                        originalLanguage TEXT NOT NULL,
                        voteAverage REAL NOT NULL,
                        voteCount INTEGER NOT NULL,
                        popularity REAL NOT NULL,
                        adult INTEGER NOT NULL,
                        video INTEGER NOT NULL,
                        genreIds TEXT NOT NULL,
                        posterPathUrlConverted TEXT,
                        backdropPathUrlConverted TEXT
                    )
                """.trimIndent())

                // Copy existing data to new table with default values for new columns
                db.execSQL("""
                    INSERT INTO movie_table_new (
                        id, title, originalTitle, overview, posterPath, backdropPath,
                        releaseDate, originalLanguage, voteAverage, voteCount,
                        popularity, adult, video, genreIds, posterPathUrlConverted, backdropPathUrlConverted
                    )
                    SELECT
                        id,
                        title,
                        title as originalTitle,
                        overview,
                        NULL as posterPath,
                        NULL as backdropPath,
                        releaseDate,
                        'en' as originalLanguage,
                        voteAverage,
                        0 as voteCount,
                        0.0 as popularity,
                        0 as adult,
                        0 as video,
                        '' as genreIds,
                        imgUrl as posterPathUrlConverted,
                        NULL as backdropPathUrlConverted
                    FROM movie_table
                """.trimIndent())

                // Drop old table
                db.execSQL("DROP TABLE movie_table")

                // Rename new table
                db.execSQL("ALTER TABLE movie_table_new RENAME TO movie_table")
            }
        }
    }
}
