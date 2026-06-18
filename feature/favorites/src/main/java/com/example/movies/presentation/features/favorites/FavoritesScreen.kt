package com.example.movies.presentation.features.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.feature.favorites.R
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
import com.example.movies.presentation.designsystem.theme.AppTextSecondary

@Composable
fun FavoritesScreen(
    movies: List<Movie>,
    onFavoriteClick: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
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
            key = "favoritesTitle",
            contentType = "sectionTitle",
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Text(
                text = stringResource(R.string.favorites_title),
                color = AppTextPrimary,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = MovieGridTitleBottomPadding),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (movies.isEmpty()) {
            item(
                key = "emptyFavorites",
                contentType = "emptyFavorites",
                span = { GridItemSpan(maxLineSpan) }
            ) {
                EmptyFavoritesMessage()
            }
        } else {
            items(
                items = movies,
                key = { movie -> movie.id },
                contentType = { "favoriteMovie" }
            ) { movie ->
                MovieGridCell(
                    movie = movie,
                    isFavorite = true,
                    onFavoriteClick = onFavoriteClick,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun EmptyFavoritesMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty_favorites_message),
            color = AppTextSecondary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}
