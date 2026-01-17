package com.example.movielibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movielibrary.ui.theme.ComicsLibraryTheme
import com.example.movielibrary.view.CharacterBottomNav
import com.example.movielibrary.view.CharacterDetailScreen
import com.example.movielibrary.view.CollectionScreen
import com.example.movielibrary.view.LibraryScreen
import com.example.movielibrary.view.MovieDetailsScreen
import com.example.movielibrary.viewmodel.CollectionViewModel
import com.example.movielibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val libraryViewModel: LibraryApiViewModel by viewModels()
        val collectionViewModel: CollectionViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
                CharacterScaffold(
                    libraryViewModel = libraryViewModel,
                    collectionViewModel = collectionViewModel
                )
            }
        }
    }
}

@Composable
fun CharacterScaffold(libraryViewModel: LibraryApiViewModel, collectionViewModel: CollectionViewModel) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier,
        topBar = {},
        bottomBar = {
            CharacterBottomNav(navController)
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = NavDestinationRoutes.Library.route
        ) {
            composable(NavDestinationRoutes.Library.route) {
                LibraryScreen(
                    navController = navController,
                    lvm = libraryViewModel,
                    modifier = Modifier,
                    paddingValues = paddingValues)

            }
            composable(NavDestinationRoutes.Collection.route) {
                CollectionScreen(
                    cvm = collectionViewModel,
                    paddingValues = paddingValues,
                    navController = navController
                )
            }
            composable(NavDestinationRoutes.MovieDetail.route) {
                CharacterDetailScreen()
            }

            composable("movieDetail/{movieId}") {
                val movieId = it.arguments?.getString("movieId")?.toIntOrNull()
                MovieDetailsScreen(
                    lvm = libraryViewModel,
                    cvm = collectionViewModel,
                    paddingValues = paddingValues,
                    navController = navController,
                    movieId = movieId
                )
            }
        }
    }
}


// https://www.perplexity.ai/search/i-need-to-find-the-class-so-th-86abOAmbQnivsk5hMKY1sg#18
