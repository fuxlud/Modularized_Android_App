package com.example.movies.data.mappers

import com.example.movies.data.remote.MoviesApiConfig
import com.example.movies.data.remote.dto.MovieDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MovieDtoMapperTest {
    @Test
    fun toMovieMapsPosterUrlsWhenPosterPathExists() {
        val dto = MovieDto(
            id = 1,
            title = "Movie",
            posterPath = "/poster.jpg",
            rating = 8.5,
            overview = "Overview",
            releaseDate = "2026-06-17"
        )

        val movie = dto.toMovie()

        assertEquals(1, movie.id)
        assertEquals("Movie", movie.title)
        assertEquals("${MoviesApiConfig.posterBaseUrl}/poster.jpg", movie.posterUrl)
        assertEquals("${MoviesApiConfig.detailPosterBaseUrl}/poster.jpg", movie.detailPosterUrl)
        assertEquals(8.5, movie.rating, 0.0)
        assertEquals("Overview", movie.overview)
        assertEquals("2026-06-17", movie.releaseDate)
    }

    @Test
    fun toMovieKeepsPosterUrlsNullWhenPosterPathIsNull() {
        val dto = MovieDto(
            id = 2,
            title = "Movie without poster",
            posterPath = null,
            rating = 7.0,
            overview = "",
            releaseDate = ""
        )

        val movie = dto.toMovie()

        assertNull(movie.posterUrl)
        assertNull(movie.detailPosterUrl)
    }
}
