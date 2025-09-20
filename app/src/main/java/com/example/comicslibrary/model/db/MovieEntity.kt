package com.example.comicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.comicslibrary.model.Movie
import com.example.comicslibrary.model.db.Constants.MOVIE_TABLE
import com.example.comicslibrary.view.ImageUrlTemplate
import com.example.comicslibrary.view.ImageUrlTemplate.toTMDBImageUrl

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) // Use movie's actual ID instead of auto-generated
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val originalLanguage: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val genreIds: String, // Store as comma-separated string since Room doesn't support List<Int> directly
    val posterPathUrlConverted: String?, // Full poster URL
    val backdropPathUrlConverted: String? // Full backdrop URL
) {
    companion object {
        fun fromMovie(movie: Movie): MovieEntity = MovieEntity(
            id = movie.id,
            title = movie.title,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            releaseDate = movie.releaseDate,
            originalLanguage = movie.originalLanguage,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            popularity = movie.popularity,
            adult = movie.adult,
            video = movie.video,
            genreIds = movie.genreIds.joinToString(","), // Convert List<Int> to comma-separated string
            posterPathUrlConverted = movie.posterPath?.toTMDBImageUrl(ImageUrlTemplate.POSTER_MEDIUM),
            backdropPathUrlConverted = movie.backdropPath?.toTMDBImageUrl(ImageUrlTemplate.BACKDROP_LARGE)
        )

        fun MovieEntity.toMovie(): Movie = Movie(
            id = this.id,
            title = this.title,
            originalTitle = this.originalTitle,
            overview = this.overview,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            releaseDate = this.releaseDate,
            originalLanguage = this.originalLanguage,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            popularity = this.popularity,
            adult = this.adult,
            video = this.video,
            genreIds = if (this.genreIds.isNotEmpty()) {
                this.genreIds.split(",").mapNotNull { it.toIntOrNull() }
            } else {
                emptyList()
            }
        )
    }
}

