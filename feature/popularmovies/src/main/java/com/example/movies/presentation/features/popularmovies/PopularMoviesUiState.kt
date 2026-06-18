package com.example.movies.presentation.features.popularmovies

import com.example.movies.domain.entities.Movie

sealed interface PopularMoviesUiState {
    data object Loading : PopularMoviesUiState
    data class Error(val message: String) : PopularMoviesUiState
    data class Loaded(
        val movies: List<Movie>,
        val isLoadingNextPage: Boolean = false
    ) : PopularMoviesUiState
}
