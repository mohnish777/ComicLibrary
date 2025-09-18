package com.example.comicslibrary

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
import com.example.comicslibrary.ui.theme.ComicsLibraryTheme
import com.example.comicslibrary.view.CharacterBottomNav
import com.example.comicslibrary.view.CharacterDetailScreen
import com.example.comicslibrary.view.CollectionScreen
import com.example.comicslibrary.view.LibraryScreen
import com.example.comicslibrary.view.MovieDetailsScreen
import com.example.comicslibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: LibraryApiViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
                CharacterScaffold(viewModel)
            }
        }
    }
}

@Composable
fun CharacterScaffold(viewModel: LibraryApiViewModel) {
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
                    vm = viewModel,
                    modifier = Modifier,
                    paddingValues = paddingValues)

            }
            composable(NavDestinationRoutes.Collection.route) {
                CollectionScreen()
            }
            composable(NavDestinationRoutes.MovieDetail.route) {
                CharacterDetailScreen()
            }

            composable("movieDetail/{movieId}") {
                val movieId = it.arguments?.getString("movieId")?.toIntOrNull()
                MovieDetailsScreen(
                    lvm = viewModel,
                    paddingValues = paddingValues,
                    navController = navController,
                    movieId = movieId
                )
            }
        }
    }
}
