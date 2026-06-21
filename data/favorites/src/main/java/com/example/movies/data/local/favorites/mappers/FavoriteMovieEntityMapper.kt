package com.example.movies.data.local.favorites.mappers

import com.example.movies.data.local.favorites.FavoriteMovieEntity
import com.example.movies.domain.entities.Movie

fun FavoriteMovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterUrl,
        rating = rating,
        overview = overview,
        releaseDate = releaseDate,
        detailPosterUrl = detailPosterUrl
    )
}

fun Movie.toFavoriteMovieEntity(savedAtMillis: Long): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        posterUrl = posterUrl,
        rating = rating,
        overview = overview,
        releaseDate = releaseDate,
        detailPosterUrl = detailPosterUrl,
        savedAtMillis = savedAtMillis
    )
}
