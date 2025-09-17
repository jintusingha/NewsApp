package com.jintu.news_app.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = Color.White
    ){

        NavigationBarItem(
            selected = currentRoute == "news_list",
            onClick = { navController.navigate("news_list") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home",
                modifier = Modifier.size(20.dp) ) },
            label = { Text("Home",
                style = MaterialTheme.typography.labelSmall ) },
            colors = NavigationBarItemDefaults.colors(
                Color.Blue,
                Color.Blue,
                indicatorColor = Color.Blue.copy(alpha = 0.1f)

            )
        )


        NavigationBarItem(
            selected = currentRoute == "bookmarks",
            onClick = { navController.navigate("bookmarks") },
            icon = { Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks",
                modifier = Modifier.size(20.dp)) },
            label = { Text("Bookmarks",
                style = MaterialTheme.typography.labelSmall ) },
            colors = NavigationBarItemDefaults.colors(
                Color.Blue,
                Color.Blue,
                indicatorColor = Color.Blue.copy(alpha = 0.1f)
            )
        )
    }
}