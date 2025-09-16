package com.example.comicslibrary.view

object ImageUrlTemplate {

    // Base URL for TMDB images
    const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    // Common image sizes
    // Poster sizes
    const val POSTER_SMALL = "w185"      // Good for thumbnails
    const val POSTER_MEDIUM = "w342"     // Good for list items
    const val POSTER_LARGE = "w500"      // Good for detail views
    const val POSTER_XLARGE = "w780"     // High quality

    // Backdrop sizes
    const val BACKDROP_SMALL = "w300"    // Thumbnails
    const val BACKDROP_MEDIUM = "w780"   // Standard
    const val BACKDROP_LARGE = "w1280"   // High quality
    const val BACKDROP_ORIGINAL = "original" // Full resolution


    // Extension functions for easy URL construction
    fun String?.toTMDBImageUrl(size: String = POSTER_MEDIUM): String? {
        return this?.let { "$TMDB_IMAGE_BASE_URL$size$it" }
    }

}
