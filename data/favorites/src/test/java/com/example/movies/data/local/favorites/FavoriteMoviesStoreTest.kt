package com.example.movies.data.local.favorites

import com.example.movies.domain.entities.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoriteMoviesStoreTest {
    private val dao = FakeFavoriteMoviesDao()
    private val store = FavoriteMoviesStore(dao)

    @Test
    fun toggleMovieAddsMovieWhenItIsNotFavorite() = runTest {
        val movie = movie(id = 1, title = "New Favorite")

        val favorites = store.toggleMovie(movie)

        assertEquals(listOf(movie), favorites)
        assertTrue(dao.isFavorite(movie.id))
    }

    @Test
    fun toggleMovieRemovesMovieWhenItIsAlreadyFavorite() = runTest {
        val movie = movie(id = 2, title = "Existing Favorite")
        store.toggleMovie(movie)

        val favorites = store.toggleMovie(movie)

        assertEquals(emptyList<Movie>(), favorites)
        assertFalse(dao.isFavorite(movie.id))
    }

    private fun movie(id: Int, title: String): Movie {
        return Movie(
            id = id,
            title = title,
            posterUrl = "/poster-$id.jpg",
            rating = 8.0,
            overview = "Overview",
            releaseDate = "2026-06-21",
            detailPosterUrl = "/detail-$id.jpg"
        )
    }
}

private class FakeFavoriteMoviesDao : FavoriteMoviesDao {
    private val movies = linkedMapOf<Int, FavoriteMovieEntity>()

    override suspend fun getMovies(): List<FavoriteMovieEntity> {
        return movies.values.sortedBy { it.savedAtMillis }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return movieId in movies
    }

    override suspend fun upsertMovie(movie: FavoriteMovieEntity) {
        movies[movie.id] = movie
    }

    override suspend fun deleteMovie(movieId: Int) {
        movies.remove(movieId)
    }
}
