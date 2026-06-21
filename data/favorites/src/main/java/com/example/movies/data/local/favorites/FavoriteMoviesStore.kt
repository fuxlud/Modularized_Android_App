package com.example.movies.data.local.favorites

import com.example.movies.data.local.favorites.mappers.toFavoriteMovieEntity
import com.example.movies.data.local.favorites.mappers.toMovie
import com.example.movies.domain.entities.Movie

class FavoriteMoviesStore(
    private val favoriteMoviesDao: FavoriteMoviesDao
) {

    suspend fun getMovies(): List<Movie> {
        return favoriteMoviesDao.getMovies().map { it.toMovie() }
    }

    suspend fun toggleMovie(movie: Movie): List<Movie> {
        if (favoriteMoviesDao.isFavorite(movie.id)) {
            favoriteMoviesDao.deleteMovie(movie.id)
        } else {
            favoriteMoviesDao.upsertMovie(
                movie.toFavoriteMovieEntity(savedAtMillis = System.currentTimeMillis())
            )
        }

        return getMovies()
    }
}
