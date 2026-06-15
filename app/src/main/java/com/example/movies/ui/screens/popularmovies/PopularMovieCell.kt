package com.example.movies.ui.screens.popularmovies

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PopularMovieCell(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MoviePoster(
            posterUrl = movie.posterUrl,
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
                text = String.format("%.1f", movie.rating),
                color = Color(0xFFD9DDE5),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun MoviePoster(posterUrl: String?, modifier: Modifier = Modifier) {
    var poster by remember(posterUrl) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(posterUrl) {
        poster = posterUrl?.let { url ->
            withContext(Dispatchers.IO) {
                runCatching {
                    val bytes = HttpClient(Android).use { client ->
                        client.get(url).bodyAsBytes()
                    }
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
                }.getOrNull()
            }
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF182A3E))
    ) {
        val image = poster
        if (image != null) {
            Image(
                bitmap = image,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        } else {
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
        Text(
            text = "♡",
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
                .size(34.dp)
        )
    }
}
