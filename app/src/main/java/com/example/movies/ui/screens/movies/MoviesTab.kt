package com.example.movies.ui.screens.movies

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class MoviesTab(
    val title: String,
    val icon: ImageVector
) {
    Discover("Discover", Icons.Default.Home),
    Favorites("Favorites", Icons.Default.Favorite)
}
