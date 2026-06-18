package com.example.movies.presentation.features.popularmovies

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.movies.domain.repositories.MoviesRepository

@Composable
fun rememberPopularMoviesState(
    repository: MoviesRepository
): PopularMoviesState {
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    return remember(repository, scope, gridState) {
        PopularMoviesState(
            gridState = gridState,
            repository = repository,
            scope = scope
        )
    }
}
