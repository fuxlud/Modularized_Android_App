package com.example.movies.ui.screens.popularmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.model.Movie

@Composable
fun PopularMoviesScreen(
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    popularMoviesState: PopularMoviesState = rememberPopularMoviesState()
) {
    val defaultErrorMessage = stringResource(R.string.popular_movies_error)

    LaunchedEffect(popularMoviesState) {
        popularMoviesState.loadInitialPage(defaultErrorMessage)
    }

    PopularMoviesContent(
        state = popularMoviesState.uiState,
        favoriteMovieIds = favoriteMovieIds,
        onFavoriteClick = onFavoriteClick,
        onMovieClick = onMovieClick,
        modifier = modifier,
        gridState = popularMoviesState.gridState,
        onLoadNextPage = popularMoviesState::loadNextPage
    )
}
