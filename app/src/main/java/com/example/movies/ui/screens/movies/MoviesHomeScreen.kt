package com.example.movies.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
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
import com.example.movies.ui.screens.popularmovies.rememberPopularMoviesState
import com.example.movies.ui.theme.AppBackgroundGradient

@Composable
fun MoviesHomeScreen(
    favoriteMovies: List<Movie>,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(MoviesTab.Discover) }
    val favoriteMovieIds = remember(favoriteMovies) { favoriteMovies.map { it.id }.toSet() }
    val popularMoviesState = rememberPopularMoviesState()
    val tabs = MoviesTab.entries

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundGradient)
    ) {
        when (selectedTab) {
            MoviesTab.Discover -> PopularMoviesScreen(
                favoriteMovieIds = favoriteMovieIds,
                onFavoriteClick = onFavoriteClick,
                popularMoviesState = popularMoviesState,
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xCC00050A))
                .navigationBarsPadding(),
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                tonalElevation = 0.dp
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
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
}
