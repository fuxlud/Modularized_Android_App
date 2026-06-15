package com.example.movies.data

import com.example.movies.data.remote.MoviesApi
import com.example.movies.model.Movie

class MovieRepository(
    private val api: MoviesApi = MoviesApi()
) {
    suspend fun getPopularMovies(): List<Movie> = api.getPopularMovies()
}
