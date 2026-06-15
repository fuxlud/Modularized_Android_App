package com.example.movies.ui.screens.popularmovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.R

private val ScreenGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF11263C),
        Color(0xFF0A1826),
        Color(0xFF000409)
    )
)

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

        is PopularMoviesUiState.Loaded -> LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier
                .fillMaxSize()
                .background(ScreenGradient),
            contentPadding = PaddingValues(
                start = 18.dp,
                top = 138.dp,
                end = 18.dp,
                bottom = 24.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier.padding(bottom = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    Text(
                        text = "Discover",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Popular Movies ›",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

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
            .background(ScreenGradient)
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
