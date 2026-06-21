package com.example.movies.data.local.favorites.mappers

import com.example.movies.data.local.favorites.FavoriteMovieEntity
import com.example.movies.domain.entities.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteMovieEntityMapperTest {
    @Test
    fun toMovieMapsAllMovieFields() {
        val entity = FavoriteMovieEntity(
            id = 1,
            title = "Movie",
            posterUrl = "/poster.jpg",
            rating = 8.5,
            overview = "Overview",
            releaseDate = "2026-06-21",
            detailPosterUrl = "/detail.jpg",
            savedAtMillis = 123L
        )

        val movie = entity.toMovie()

        assertEquals(
            Movie(
                id = 1,
                title = "Movie",
                posterUrl = "/poster.jpg",
                rating = 8.5,
                overview = "Overview",
                releaseDate = "2026-06-21",
                detailPosterUrl = "/detail.jpg"
            ),
            movie
        )
    }

    @Test
    fun toFavoriteMovieEntityAddsSavedTimestamp() {
        val movie = Movie(
            id = 2,
            title = "Favorite",
            posterUrl = null,
            rating = 7.5,
            overview = "Overview",
            releaseDate = "2026-06-20",
            detailPosterUrl = null
        )

        val entity = movie.toFavoriteMovieEntity(savedAtMillis = 456L)

        assertEquals(
            FavoriteMovieEntity(
                id = 2,
                title = "Favorite",
                posterUrl = null,
                rating = 7.5,
                overview = "Overview",
                releaseDate = "2026-06-20",
                detailPosterUrl = null,
                savedAtMillis = 456L
            ),
            entity
        )
    }
}
