package com.example.movies.data.remote

import com.example.movies.BuildConfig

object MoviesApiConfig {
    const val popularMoviesUrl = "https://api.themoviedb.org/3/movie/popular"
    const val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
    const val detailPosterBaseUrl = "https://image.tmdb.org/t/p/w780"

    val apiKey: String = BuildConfig.TMDB_API_KEY
}
