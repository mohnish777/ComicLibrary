package com.example.comicslibrary

import android.content.Context
import androidx.room.Room
import com.example.comicslibrary.model.api.ApiService
import com.example.comicslibrary.model.db.Constants.DB
import com.example.comicslibrary.model.db.MovieDB
import com.example.comicslibrary.model.db.MovieDao
import com.example.comicslibrary.model.repository.MovieApiRepo
import com.example.comicslibrary.model.repository.MovieDbRepoImpl
import com.example.comicslibrary.model.repository.MovieDbRepoInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun providesApiRepo() = MovieApiRepo(ApiService.api)

    @Provides
    fun provideMovieDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDB::class.java, DB).build()

    @Provides
    fun provideMovieDao(movieDb: MovieDB) = movieDb.movieDao()

    @Provides
    fun provideMovieDbRepo(movieDao: MovieDao): MovieDbRepoInterface = MovieDbRepoImpl(movieDao)
}
