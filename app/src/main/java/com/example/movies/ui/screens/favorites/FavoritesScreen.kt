package com.example.movies.ui.screens.favorites

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.components.MovieGridCell
import com.example.movies.ui.theme.AppBackgroundGradient
import com.example.movies.ui.theme.AppTextPrimary
import com.example.movies.ui.theme.AppTextSecondary

@Composable
fun FavoritesScreen(
    movies: List<Movie>,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundGradient),
        contentPadding = PaddingValues(
            start = 18.dp,
            top = 69.dp,
            end = 18.dp,
            bottom = 116.dp
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
                    text = stringResource(R.string.favorites_title),
                    color = AppTextPrimary,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.favorites_subtitle),
                    color = AppTextPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (movies.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
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
                    onFavoriteClick = onFavoriteClick
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
