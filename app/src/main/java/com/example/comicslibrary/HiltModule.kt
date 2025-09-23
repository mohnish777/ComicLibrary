package com.example.comicslibrary

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.comicslibrary.model.api.ApiService
import com.example.comicslibrary.model.db.Constants.DB
import com.example.comicslibrary.model.db.MovieDB
import com.example.comicslibrary.model.db.MovieDao
import com.example.comicslibrary.model.db.NoteDao
import com.example.comicslibrary.model.repository.MovieApiRepo
import com.example.comicslibrary.model.repository.MovieDbRepoImpl
import com.example.comicslibrary.model.repository.MovieDbRepoInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    @Provides
    @Singleton
    fun providesApiRepo() = MovieApiRepo(ApiService.api)

    @Provides
    @Singleton
    fun provideMovieDb(@ApplicationContext context: Context): MovieDB {
        val instance = Room.databaseBuilder(context, MovieDB::class.java, DB)
            .addMigrations(MovieDB.MIGRATION_1_2)
            .fallbackToDestructiveMigration() // Temporary: recreates DB if migration fails
            .build()
        Log.d("HILT_DB", "Database created: ${instance.hashCode()}")
        return instance
    }


    @Provides
    fun provideMovieDao(movieDb: MovieDB): MovieDao = movieDb.movieDao()

    @Provides
    fun providesNoteDao(movieDb: MovieDB): NoteDao = movieDb.noteDao()

    @Provides
    fun provideMovieDbRepo(movieDao: MovieDao, noteDao: NoteDao): MovieDbRepoInterface = MovieDbRepoImpl(movieDao, noteDao)
}
