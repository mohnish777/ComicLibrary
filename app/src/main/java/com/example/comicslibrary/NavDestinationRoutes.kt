package com.example.comicslibrary

import androidx.annotation.DrawableRes

sealed class NavDestinationRoutes(val route: String, @DrawableRes val iconRes: Int, val label: String) {
    object Library : NavDestinationRoutes(route = "library", iconRes = R.drawable.ic_library, label = "Library")
    object Collection : NavDestinationRoutes(route = "collection", R.drawable.ic_collection, label = "Collection")
    object MovieDetail : NavDestinationRoutes(route = "movie/{movieID}", R.drawable.ic_collection, label = "Movie Detail") {
        fun createRoute(movieId: Int?) = "movie/$movieId"
    }

    companion object {
        val allRoutes = listOf(
            Library,
            Collection,
            MovieDetail
        )
    }
}
