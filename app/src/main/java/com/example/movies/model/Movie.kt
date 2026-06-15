package com.example.movies.model

import androidx.compose.runtime.Immutable

@Immutable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?
)
