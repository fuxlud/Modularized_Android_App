package com.example.movies.ui.screens.popularmovies

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.example.movies.ui.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
private fun PopularMoviesScreenPreview() {
    MoviesTheme(darkTheme = true) {
        PopularMoviesContent(
            state = PopularMoviesUiState.Loaded(samplePopularMovies),
            favoriteMovieIds = setOf(1),
            onFavoriteClick = {},
            gridState = rememberLazyGridState()
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
private fun PopularMoviesScreenDarkPreview() {
    MoviesTheme(darkTheme = true) {
        PopularMoviesContent(
            state = PopularMoviesUiState.Loaded(samplePopularMovies),
            favoriteMovieIds = setOf(1),
            onFavoriteClick = {},
            gridState = rememberLazyGridState()
        )
    }
}
