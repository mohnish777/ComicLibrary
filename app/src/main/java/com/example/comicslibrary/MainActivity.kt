package com.example.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
                CharacterScaffold()
            }
        }
    }
}

@Composable
fun CharacterScaffold() {
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
                LibraryScreen(modifier = Modifier.padding(paddingValues))
            }
            composable(NavDestinationRoutes.Collection.route) {
                CollectionScreen()
            }
            composable(NavDestinationRoutes.CharacterDetail.route) {
                CharacterDetailScreen()
            }
        }
    }
}
