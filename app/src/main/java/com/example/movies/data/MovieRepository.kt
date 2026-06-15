package com.example.movies.data

import com.example.movies.data.remote.MoviesApi
import com.example.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val api: MoviesApi = MoviesApi()
) {
    suspend fun getPopularMovies(page: Int): List<Movie> = withContext(Dispatchers.IO) {
        api.getPopularMovies(page)
    }
}
