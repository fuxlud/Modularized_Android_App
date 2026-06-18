package com.example.movies.domain.repositories

import com.example.movies.domain.entities.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
}
