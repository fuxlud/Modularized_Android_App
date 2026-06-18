package com.example.movies.presentation.features.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movies.core.designsystem.R as DesignSystemR
import com.example.movies.feature.moviedetails.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.designsystem.theme.AppBackgroundGradient
import com.example.movies.presentation.designsystem.theme.AppTextPrimary
import com.example.movies.presentation.designsystem.theme.AppTextSecondary
import com.example.movies.presentation.designsystem.theme.FavoriteSelected
import com.example.movies.presentation.designsystem.theme.MoviePosterPlaceholderBackground
import com.example.movies.presentation.designsystem.theme.MoviePosterPlaceholderIcon
import com.example.movies.presentation.designsystem.theme.MovieRatingAccent

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundGradient)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.movie_details_back),
                    tint = AppTextPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onFavoriteClick(movie) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.movie_details_favorite),
                    tint = if (isFavorite) FavoriteSelected else AppTextPrimary
                )
            }
        }

        MovieDetailsPoster(
            posterUrl = movie.detailPosterUrl ?: movie.posterUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.72f)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = movie.title,
                color = AppTextPrimary,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.movie_details_rating,
                    stringResource(DesignSystemR.string.rating_star),
                    movie.rating
                ),
                color = MovieRatingAccent,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (movie.releaseDate.isNotBlank()) {
                Text(
                    text = stringResource(R.string.movie_details_release_date, movie.releaseDate),
                    color = AppTextSecondary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.movie_details_overview_title),
                color = AppTextPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.overview.ifBlank { stringResource(R.string.movie_details_no_overview) },
                color = AppTextSecondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun MovieDetailsPoster(
    posterUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MoviePosterPlaceholderBackground),
        contentAlignment = Alignment.Center
    ) {
        if (posterUrl == null) {
            Text(
                text = stringResource(DesignSystemR.string.movie_poster_placeholder),
                color = MoviePosterPlaceholderIcon,
                style = MaterialTheme.typography.displayMedium
            )
        }
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}
