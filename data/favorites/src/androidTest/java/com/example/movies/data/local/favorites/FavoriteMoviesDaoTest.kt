package com.example.movies.data.local.favorites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteMoviesDaoTest {
    private lateinit var database: FavoriteMoviesDatabase
    private lateinit var dao: FavoriteMoviesDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            FavoriteMoviesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.favoriteMoviesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getMoviesReturnsFavoritesOrderedBySavedTime() = runTest {
        val newest = entity(id = 1, title = "Newest", savedAtMillis = 200L)
        val oldest = entity(id = 2, title = "Oldest", savedAtMillis = 100L)

        dao.upsertMovie(newest)
        dao.upsertMovie(oldest)

        assertEquals(listOf(oldest, newest), dao.getMovies())
    }

    @Test
    fun deleteMovieRemovesFavorite() = runTest {
        val movie = entity(id = 3, title = "Favorite", savedAtMillis = 300L)
        dao.upsertMovie(movie)

        assertTrue(dao.isFavorite(movie.id))

        dao.deleteMovie(movie.id)

        assertFalse(dao.isFavorite(movie.id))
    }

    private fun entity(
        id: Int,
        title: String,
        savedAtMillis: Long
    ): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            id = id,
            title = title,
            posterUrl = "/poster-$id.jpg",
            rating = 8.0,
            overview = "Overview",
            releaseDate = "2026-06-21",
            detailPosterUrl = "/detail-$id.jpg",
            savedAtMillis = savedAtMillis
        )
    }
}
