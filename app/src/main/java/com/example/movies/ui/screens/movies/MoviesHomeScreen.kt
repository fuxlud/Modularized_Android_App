package com.example.movies.ui.screens.movies

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.model.Movie
import com.example.movies.ui.screens.favorites.FavoritesScreen
import com.example.movies.ui.screens.moviedetails.MovieDetailsScreen
import com.example.movies.ui.screens.popularmovies.PopularMoviesScreen
import com.example.movies.ui.screens.popularmovies.PopularMoviesState
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
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    val navController = rememberNavController()
    val favoriteMovieIds = remember(favoriteMovies) { favoriteMovies.map { it.id }.toSet() }
    val popularMoviesState = rememberPopularMoviesState()
    val tabs = MoviesTab.entries

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundGradient)
    ) {
        NavHost(
            navController = navController,
            startDestination = MoviesRoute.Home.route,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween()
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween()
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween()
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween()
                )
            }
        ) {
            composable(MoviesRoute.Home.route) {
                MoviesHomeContent(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    tabs = tabs,
                    favoriteMovies = favoriteMovies,
                    favoriteMovieIds = favoriteMovieIds,
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = { movie ->
                        selectedMovie = movie
                        navController.navigate(MoviesRoute.MovieDetails.route)
                    },
                    popularMoviesState = popularMoviesState,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(MoviesRoute.MovieDetails.route) {
                val movie = selectedMovie
                if (movie == null) {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                    return@composable
                }

                MovieDetailsScreen(
                    movie = movie,
                    isFavorite = movie.id in favoriteMovieIds,
                    onBackClick = { navController.popBackStack() },
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun MoviesHomeContent(
    selectedTab: MoviesTab,
    onTabSelected: (MoviesTab) -> Unit,
    tabs: List<MoviesTab>,
    favoriteMovies: List<Movie>,
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    popularMoviesState: PopularMoviesState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (selectedTab) {
            MoviesTab.Discover -> PopularMoviesScreen(
                favoriteMovieIds = favoriteMovieIds,
                onFavoriteClick = onFavoriteClick,
                onMovieClick = onMovieClick,
                popularMoviesState = popularMoviesState,
                modifier = Modifier.fillMaxSize()
            )

            MoviesTab.Favorites -> FavoritesScreen(
                movies = favoriteMovies,
                onFavoriteClick = onFavoriteClick,
                onMovieClick = onMovieClick,
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
                        onClick = { onTabSelected(tab) },
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

private enum class MoviesRoute(val route: String) {
    Home("home"),
    MovieDetails("movieDetails")
}
