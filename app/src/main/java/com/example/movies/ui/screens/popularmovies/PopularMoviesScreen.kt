package com.example.movies.ui.screens.popularmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.movies.data.MovieRepository

@Composable
fun PopularMoviesScreen(
    modifier: Modifier = Modifier,
    repository: MovieRepository = remember { MovieRepository() }
) {
    var state by remember { mutableStateOf<PopularMoviesUiState>(PopularMoviesUiState.Loading) }

    LaunchedEffect(repository) {
        state = runCatching { repository.getPopularMovies() }
            .fold(
                onSuccess = { PopularMoviesUiState.Loaded(it) },
                onFailure = { error ->
                    PopularMoviesUiState.Error(
                        error.message ?: "Could not load popular movies."
                    )
                }
            )
    }

    PopularMoviesContent(
        state = state,
        modifier = modifier
    )
}
