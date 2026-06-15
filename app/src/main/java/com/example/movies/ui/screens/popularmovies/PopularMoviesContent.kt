package com.example.movies.ui.screens.popularmovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.movies.R

@Composable
fun PopularMoviesContent(
    state: PopularMoviesUiState,
    modifier: Modifier = Modifier
) {
    when (state) {
        PopularMoviesUiState.Loading -> PopularMoviesMessage(
            text = stringResource(R.string.loading_popular_movies),
            modifier = modifier
        )

        is PopularMoviesUiState.Error -> PopularMoviesMessage(
            text = state.message.ifBlank { stringResource(R.string.popular_movies_error) },
            modifier = modifier
        )

        is PopularMoviesUiState.Loaded -> LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.movies,
                key = { movie -> movie.id },
                contentType = { "popularMovie" }
            ) { movie ->
                PopularMovieCell(movie = movie)
            }
        }
    }
}

@Composable
private fun PopularMoviesMessage(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
