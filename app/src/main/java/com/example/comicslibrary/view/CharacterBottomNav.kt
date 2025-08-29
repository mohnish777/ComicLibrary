package com.example.comicslibrary.view

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.comicslibrary.NavDestinationRoutes

@Composable
fun CharacterBottomNav(navController: NavHostController){
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry.value?.destination

    NavigationBar(
        tonalElevation = 5.dp,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavDestinationRoutes.allRoutes.forEachIndexed { index, navDestinations ->
            NavigationBarItem(
                selected = currentDestination?.route == navDestinations.route,
                onClick = {
                    navController.navigate(route = navDestinations.route) {
                        popUpTo(navDestinations.route)
                        launchSingleTop = true
                    }

                },

                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(navDestinations.iconRes),
                        modifier = Modifier.size(16.dp),
                        contentDescription = null
                    )
                },

                label = {
                    Text(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        text = navDestinations.label,
                        textAlign = TextAlign.Center
                    )
                }
            )

        }

    }
}
