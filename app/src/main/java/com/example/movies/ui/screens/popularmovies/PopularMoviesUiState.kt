package com.example.movies.ui.screens.popularmovies

import com.example.movies.model.Movie

sealed interface PopularMoviesUiState {
    data object Loading : PopularMoviesUiState
    data class Error(val message: String) : PopularMoviesUiState
    data class Loaded(
        val movies: List<Movie>,
        val isLoadingNextPage: Boolean = false
    ) : PopularMoviesUiState
}
