package com.example.movies.data.repositories

import com.example.movies.data.mappers.toMovie
import com.example.movies.data.remote.MoviesApi
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TmdbMoviesRepository(
    private val api: MoviesApi
) : MoviesRepository {
    override suspend fun getPopularMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        api.getPopularMovies(page).map { it.toMovie() }
    }
}
