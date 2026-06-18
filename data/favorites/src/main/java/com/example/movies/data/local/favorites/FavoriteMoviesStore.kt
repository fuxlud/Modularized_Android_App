package com.example.movies.data.local.favorites

import android.content.Context
import com.example.movies.domain.entities.Movie
import org.json.JSONArray
import org.json.JSONObject

class FavoriteMoviesStore(context: Context) {
    private val preferences = context.getSharedPreferences("favorite_movies", Context.MODE_PRIVATE)

    fun getMovies(): List<Movie> {
        val rawMovies = preferences.getString(KEY_MOVIES, null) ?: return emptyList()
        val movies = JSONArray(rawMovies)

        return (0 until movies.length()).map { index ->
            val item = movies.getJSONObject(index)
            Movie(
                id = item.getInt("id"),
                title = item.getString("title"),
                posterUrl = item.optString("posterUrl").takeIf { it.isNotBlank() },
                rating = item.optDouble("rating"),
                overview = item.optString("overview"),
                releaseDate = item.optString("releaseDate"),
                detailPosterUrl = item.optString("detailPosterUrl").takeIf { it.isNotBlank() }
            )
        }
    }

    fun toggleMovie(movie: Movie): List<Movie> {
        val currentMovies = getMovies()
        val updatedMovies = if (currentMovies.any { it.id == movie.id }) {
            currentMovies.filterNot { it.id == movie.id }
        } else {
            currentMovies + movie
        }

        saveMovies(updatedMovies)
        return updatedMovies
    }

    private fun saveMovies(movies: List<Movie>) {
        val payload = JSONArray().apply {
            movies.forEach { movie ->
                put(
                    JSONObject()
                        .put("id", movie.id)
                        .put("title", movie.title)
                        .put("posterUrl", movie.posterUrl.orEmpty())
                        .put("rating", movie.rating)
                        .put("overview", movie.overview)
                        .put("releaseDate", movie.releaseDate)
                        .put("detailPosterUrl", movie.detailPosterUrl.orEmpty())
                )
            }
        }

        preferences.edit()
            .putString(KEY_MOVIES, payload.toString())
            .apply()
    }

    private companion object {
        const val KEY_MOVIES = "movies"
    }
}
