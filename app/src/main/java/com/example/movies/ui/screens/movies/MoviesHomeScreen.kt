package com.example.movies.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movies.model.Movie
import com.example.movies.ui.screens.favorites.FavoritesScreen
import com.example.movies.ui.screens.popularmovies.PopularMoviesScreen

private val ScreenGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF11263C),
        Color(0xFF0A1826),
        Color(0xFF000409)
    )
)

@Composable
fun MoviesHomeScreen(
    favoriteMovies: List<Movie>,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(MoviesTab.Discover) }
    val favoriteMovieIds = remember(favoriteMovies) { favoriteMovies.map { it.id }.toSet() }
    val tabs = MoviesTab.entries

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenGradient)
    ) {
        when (selectedTab) {
            MoviesTab.Discover -> PopularMoviesScreen(
                favoriteMovieIds = favoriteMovieIds,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.fillMaxSize()
            )

            MoviesTab.Favorites -> FavoritesScreen(
                movies = favoriteMovies,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(116.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xDD00050A)
                        )
                    )
                )
        )

        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding(),
            containerColor = Color(0xCC00050A),
            contentColor = Color.White,
            tonalElevation = 0.dp
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    icon = {
                        Text(
                            text = tab.icon,
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (selectedTab == tab) Color.White else Color(0xFF9BA8B8)
                        )
                    },
                    label = {
                        Text(
                            text = tab.title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xAA173756),
                        unselectedIconColor = Color(0xFF9BA8B8),
                        unselectedTextColor = Color(0xFF9BA8B8)
                    )
                )
            }
        }
    }
}
