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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movies.model.Movie
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
            color = Color.White,
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
                text = "★",
                color = Color(0xFF43A7FF),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = ratingText,
                color = Color(0xFFD9DDE5),
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
            .background(Color(0xFF182A3E))
    ) {
        if (posterUrl == null) {
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎬",
                    color = Color(0xFF6E7C8C),
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
        Text(
            text = if (isFavorite) "♥" else "♡",
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
                .size(34.dp)
                .clickable(onClick = onFavoriteClick)
        )
    }
}
