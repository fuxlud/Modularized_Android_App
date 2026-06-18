package com.example.movies.data.remote

import com.example.movies.data.remote.dto.MovieDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject

class MoviesApi(
    private val client: HttpClient
) {
    suspend fun getPopularMovies(page: Int): List<MovieDto> {
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

            MovieDto(
                id = item.getInt("id"),
                title = item.getString("title"),
                posterPath = posterPath,
                rating = item.optDouble("vote_average"),
                overview = item.optString("overview"),
                releaseDate = item.optString("release_date")
            )
        }
    }
}
