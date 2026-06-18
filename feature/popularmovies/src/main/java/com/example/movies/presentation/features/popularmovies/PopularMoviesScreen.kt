package com.example.movies.presentation.features.popularmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.feature.popularmovies.R
import com.example.movies.domain.entities.Movie

@Composable
fun PopularMoviesScreen(
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    popularMoviesState: PopularMoviesState,
    modifier: Modifier = Modifier,
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
