package com.example.movies.ui.screens.popularmovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.components.MovieGridCell
import com.example.movies.ui.theme.AppBackgroundGradient
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PopularMoviesContent(
    state: PopularMoviesUiState,
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    onLoadNextPage: () -> Unit = {}
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

        is PopularMoviesUiState.Loaded -> {
            val gridState = rememberLazyGridState()

            LaunchedEffect(gridState, state.movies.size) {
                snapshotFlow {
                    gridState.layoutInfo.let { layoutInfo ->
                        val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        lastVisibleIndex >= layoutInfo.totalItemsCount - 7
                    }
                }
                .distinctUntilChanged()
                .collect { shouldLoadMore ->
                    if (shouldLoadMore) onLoadNextPage()
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState,
                modifier = modifier
                    .fillMaxSize()
                    .background(AppBackgroundGradient),
                contentPadding = PaddingValues(
                    start = 18.dp,
                    top = 32.dp,
                    end = 18.dp,
                    bottom = 116.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "Discover Popular Movies",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                items(
                    items = state.movies,
                    key = { movie -> movie.id },
                    contentType = { "popularMovie" }
                ) { movie ->
                    MovieGridCell(
                        movie = movie,
                        isFavorite = movie.id in favoriteMovieIds,
                        onFavoriteClick = onFavoriteClick
                    )
                }

                if (state.isLoadingNextPage) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = Color(0xFF43A7FF),
                                strokeWidth = 3.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PopularMoviesMessage(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundGradient)
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
