package com.example.movies.ui.screens.movies

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movies.R

enum class MoviesTab(
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    Discover(R.string.discover_tab_title, Icons.Default.Home),
    Favorites(R.string.favorites_tab_title, Icons.Default.Favorite)
}
