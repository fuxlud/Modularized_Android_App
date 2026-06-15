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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movies.model.Movie
import com.example.movies.ui.screens.favorites.FavoritesScreen
import com.example.movies.ui.screens.popularmovies.PopularMoviesScreen
import com.example.movies.ui.screens.popularmovies.rememberPopularMoviesState
import com.example.movies.ui.theme.AppBackgroundGradient
import com.example.movies.ui.theme.AppTextPrimary
import com.example.movies.ui.theme.AppTransparent
import com.example.movies.ui.theme.BottomNavigationContainerColor
import com.example.movies.ui.theme.BottomNavigationFadeEnd
import com.example.movies.ui.theme.BottomNavigationIndicatorColor
import com.example.movies.ui.theme.BottomNavigationUnselectedColor

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
                            AppTransparent,
                            BottomNavigationFadeEnd
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BottomNavigationContainerColor)
                .navigationBarsPadding(),
        ) {
            NavigationBar(
                containerColor = AppTransparent,
                contentColor = AppTextPrimary,
                tonalElevation = 0.dp
            ) {
                tabs.forEach { tab ->
                    val tabTitle = stringResource(tab.titleResId)
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tabTitle
                            )
                        },
                        label = {
                            Text(
                                text = tabTitle,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AppTextPrimary,
                            selectedTextColor = AppTextPrimary,
                            indicatorColor = BottomNavigationIndicatorColor,
                            unselectedIconColor = BottomNavigationUnselectedColor,
                            unselectedTextColor = BottomNavigationUnselectedColor
                        )
                    )
                }
            }
        }
    }
}
