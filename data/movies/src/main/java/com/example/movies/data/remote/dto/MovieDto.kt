package com.example.movies.data.remote.dto

data class MovieDto(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val rating: Double,
    val overview: String,
    val releaseDate: String
)
