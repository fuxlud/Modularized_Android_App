package com.example.movies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.ui.theme.AppTextPrimary
import com.example.movies.ui.theme.AppTextSecondary
import com.example.movies.ui.theme.FavoriteSelected
import com.example.movies.ui.theme.MoviePosterPlaceholderBackground
import com.example.movies.ui.theme.MoviePosterPlaceholderIcon
import com.example.movies.ui.theme.MovieRatingAccent
import java.util.Locale

@Composable
fun MovieGridCell(
    movie: Movie,
    isFavorite: Boolean,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val ratingText = remember(movie.rating) {
        String.format(Locale.US, "%.1f", movie.rating)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MoviePoster(
            posterUrl = movie.posterUrl,
            isFavorite = isFavorite,
            onFavoriteClick = { onFavoriteClick(movie) },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
        )
        Text(
            text = movie.title,
            color = AppTextPrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.padding(top = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.rating_star),
                color = MovieRatingAccent,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = ratingText,
                color = AppTextSecondary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun MoviePoster(
    posterUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MoviePosterPlaceholderBackground)
    ) {
        if (posterUrl == null) {
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.movie_poster_placeholder),
                    color = MoviePosterPlaceholderIcon,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = if (isFavorite) FavoriteSelected else AppTextPrimary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
                .size(32.dp)
                .clickable(onClick = onFavoriteClick)
        )
    }
}
