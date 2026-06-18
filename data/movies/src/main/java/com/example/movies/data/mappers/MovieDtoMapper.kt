package com.example.movies.data.mappers

import com.example.movies.data.remote.MoviesApiConfig
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.domain.entities.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "${MoviesApiConfig.posterBaseUrl}$it" },
        rating = rating,
        overview = overview,
        releaseDate = releaseDate,
        detailPosterUrl = posterPath?.let { "${MoviesApiConfig.detailPosterBaseUrl}$it" }
    )
}
