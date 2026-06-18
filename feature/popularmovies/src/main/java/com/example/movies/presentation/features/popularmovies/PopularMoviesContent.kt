package com.example.movies.presentation.features.popularmovies

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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.feature.popularmovies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.designsystem.components.MovieGridCell
import com.example.movies.presentation.designsystem.layout.MovieGridBottomPadding
import com.example.movies.presentation.designsystem.layout.MovieGridHorizontalPadding
import com.example.movies.presentation.designsystem.layout.MovieGridHorizontalSpacing
import com.example.movies.presentation.designsystem.layout.MovieGridTitleBottomPadding
import com.example.movies.presentation.designsystem.layout.MovieGridTopPadding
import com.example.movies.presentation.designsystem.layout.MovieGridVerticalSpacing
import com.example.movies.presentation.designsystem.theme.AppBackgroundGradient
import com.example.movies.presentation.designsystem.theme.AppTextPrimary
import com.example.movies.presentation.designsystem.theme.MovieRatingAccent
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PopularMoviesContent(
    state: PopularMoviesUiState,
    favoriteMovieIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
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
                    start = MovieGridHorizontalPadding,
                    top = MovieGridTopPadding,
                    end = MovieGridHorizontalPadding,
                    bottom = MovieGridBottomPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(MovieGridHorizontalSpacing),
                verticalArrangement = Arrangement.spacedBy(MovieGridVerticalSpacing)
            ) {
                item(
                    key = "popularMoviesTitle",
                    contentType = "sectionTitle",
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Text(
                        text = stringResource(R.string.popular_movies_title),
                        color = AppTextPrimary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = MovieGridTitleBottomPadding),
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
                        onFavoriteClick = onFavoriteClick,
                        onMovieClick = onMovieClick
                    )
                }

                if (state.isLoadingNextPage) {
                    item(
                        key = "popularMoviesLoadingNextPage",
                        contentType = "loadingNextPage",
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = MovieRatingAccent,
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
            color = AppTextPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
