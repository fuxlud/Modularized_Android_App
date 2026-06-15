package com.example.movies.data.remote

import com.example.movies.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject

class MoviesApi(
    private val client: HttpClient = HttpClient(Android)
) {
    suspend fun getPopularMovies(page: Int): List<Movie> {
        check(MoviesApiConfig.apiKey.isNotBlank()) {
            "Missing TMDB_API_KEY in local.properties"
        }

        val response = client
            .get("${MoviesApiConfig.popularMoviesUrl}?api_key=${MoviesApiConfig.apiKey}&language=en-US&page=$page")
            .bodyAsText()

        val results = JSONObject(response).getJSONArray("results")

        return (0 until results.length()).map { index ->
            val item = results.getJSONObject(index)
            val posterPath = item.optString("poster_path").takeIf { it.isNotBlank() && it != "null" }

            Movie(
                id = item.getInt("id"),
                title = item.getString("title"),
                posterUrl = posterPath?.let { "${MoviesApiConfig.posterBaseUrl}$it" },
                rating = item.optDouble("vote_average"),
                overview = item.optString("overview"),
                releaseDate = item.optString("release_date")
            )
        }
    }
}
