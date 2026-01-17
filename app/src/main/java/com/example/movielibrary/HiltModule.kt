package com.example.movielibrary

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.movielibrary.model.api.ApiService
import com.example.movielibrary.model.db.Constants.DB
import com.example.movielibrary.model.db.MovieDB
import com.example.movielibrary.model.db.MovieDao
import com.example.movielibrary.model.db.NoteDao
import com.example.movielibrary.model.repository.MovieApiRepo
import com.example.movielibrary.model.repository.MovieDbRepoImpl
import com.example.movielibrary.model.repository.MovieDbRepoInterface
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
