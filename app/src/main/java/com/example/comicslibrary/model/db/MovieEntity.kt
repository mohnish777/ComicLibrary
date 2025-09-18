package com.example.comicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.comicslibrary.model.Movie
import com.example.comicslibrary.model.db.Constants.MOVIE_TABLE
import com.example.comicslibrary.view.ImageUrlTemplate

@Entity(tableName = MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val imgUrl: String?,
    val releaseDate: String,
    val voteAverage: Double
) {
    companion object  {
        fun fromMovie(movie: Movie): MovieEntity = MovieEntity(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            imgUrl = movie.getPosterUrl(ImageUrlTemplate.POSTER_XLARGE),
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage
        )
    }
}

