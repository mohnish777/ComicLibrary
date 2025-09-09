package com.example.comicslibrary

import com.example.comicslibrary.model.api.ApiService
import com.example.comicslibrary.model.repository.MovieApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun providesApiRepo() = MovieApiRepo(ApiService.api)
}
