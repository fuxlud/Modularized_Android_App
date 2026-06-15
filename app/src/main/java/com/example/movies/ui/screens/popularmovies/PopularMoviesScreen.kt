package com.example.movies.ui.screens.popularmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.movies.model.Movie

@Composable
fun PopularMoviesScreen(
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    popularMoviesState: PopularMoviesState = rememberPopularMoviesState()
) {
    LaunchedEffect(popularMoviesState) {
        popularMoviesState.loadInitialPage()
    }

    PopularMoviesContent(
        state = popularMoviesState.uiState,
        favoriteMovieIds = favoriteMovieIds,
        onFavoriteClick = onFavoriteClick,
        modifier = modifier,
        gridState = popularMoviesState.gridState,
        onLoadNextPage = popularMoviesState::loadNextPage
    )
}
