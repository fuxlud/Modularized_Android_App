package com.example.movies.presentation.features.popularmovies

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PopularMoviesStateTest {
    @Test
    fun loadInitialPageLoadsFirstPageOnce() = runTest {
        val repository = FakeMoviesRepository(
            pages = mapOf(1 to listOf(movie(id = 1, title = "First")))
        )
        val state = popularMoviesState(repository)

        state.loadInitialPage(defaultErrorMessage = "Could not load")
        state.loadInitialPage(defaultErrorMessage = "Could not load")
        testScheduler.advanceUntilIdle()

        assertEquals(listOf(1), repository.requestedPages)
        assertEquals(
            PopularMoviesUiState.Loaded(listOf(movie(id = 1, title = "First"))),
            state.uiState
        )
    }

    @Test
    fun loadInitialPageShowsFallbackErrorWhenMessageIsMissing() = runTest {
        val repository = FakeMoviesRepository(error = IllegalStateException())
        val state = popularMoviesState(repository)

        state.loadInitialPage(defaultErrorMessage = "Could not load")
        testScheduler.advanceUntilIdle()

        assertEquals(PopularMoviesUiState.Error("Could not load"), state.uiState)
    }

    @Test
    fun loadNextPageAppendsUniqueMoviesAndStopsLoading() = runTest {
        val repository = FakeMoviesRepository(
            pages = mapOf(
                1 to listOf(movie(id = 1, title = "First")),
                2 to listOf(
                    movie(id = 1, title = "First"),
                    movie(id = 2, title = "Second")
                )
            )
        )
        val state = popularMoviesState(repository)

        state.loadInitialPage(defaultErrorMessage = "Could not load")
        testScheduler.advanceUntilIdle()
        state.loadNextPage()
        assertTrue((state.uiState as PopularMoviesUiState.Loaded).isLoadingNextPage)

        testScheduler.advanceUntilIdle()

        val loadedState = state.uiState as PopularMoviesUiState.Loaded
        assertEquals(
            listOf(
                movie(id = 1, title = "First"),
                movie(id = 2, title = "Second")
            ),
            loadedState.movies
        )
        assertFalse(loadedState.isLoadingNextPage)
    }

    private fun TestScope.popularMoviesState(repository: MoviesRepository): PopularMoviesState {
        return PopularMoviesState(
            gridState = LazyGridState(),
            repository = repository,
            scope = TestScope(StandardTestDispatcher(testScheduler))
        )
    }

    private fun movie(id: Int, title: String): Movie {
        return Movie(
            id = id,
            title = title,
            posterUrl = null,
            rating = 8.0,
            overview = "Overview",
            releaseDate = "2026-06-21",
            detailPosterUrl = null
        )
    }
}

private class FakeMoviesRepository(
    private val pages: Map<Int, List<Movie>> = emptyMap(),
    private val error: Throwable? = null
) : MoviesRepository {
    val requestedPages = mutableListOf<Int>()

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        requestedPages += page
        error?.let { throw it }
        return pages.getValue(page)
    }
}
