package com.example.movies.presentation.features.popularmovies

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.designsystem.theme.MoviesTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class PopularMoviesContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingStateShowsLoadingMessage() {
        composeRule.setPopularMoviesContent(state = PopularMoviesUiState.Loading)

        composeRule.onNodeWithText("Loading popular movies...").assertIsDisplayed()
    }

    @Test
    fun errorStateShowsProvidedMessage() {
        composeRule.setPopularMoviesContent(
            state = PopularMoviesUiState.Error("Network is taking a nap")
        )

        composeRule.onNodeWithText("Network is taking a nap").assertIsDisplayed()
    }

    @Test
    fun loadedStateShowsMoviesAndHandlesMovieClick() {
        val movie = movie(id = 1, title = "The Test Movie")
        val clickedMovies = mutableListOf<Movie>()

        composeRule.setPopularMoviesContent(
            state = PopularMoviesUiState.Loaded(listOf(movie)),
            onMovieClick = clickedMovies::add
        )

        composeRule.onNodeWithText("Discover Popular Movies").assertIsDisplayed()
        composeRule.onNodeWithText("The Test Movie").assertIsDisplayed()
        composeRule.onNodeWithText("The Test Movie").performClick()

        assertEquals(listOf(movie), clickedMovies)
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

private fun androidx.compose.ui.test.junit4.ComposeContentTestRule.setPopularMoviesContent(
    state: PopularMoviesUiState,
    favoriteMovieIds: Set<Int> = emptySet(),
    onFavoriteClick: (Movie) -> Unit = {},
    onMovieClick: (Movie) -> Unit = {}
) {
    setContent {
        MoviesTheme(dynamicColor = false) {
            PopularMoviesContent(
                state = state,
                favoriteMovieIds = favoriteMovieIds,
                onFavoriteClick = onFavoriteClick,
                onMovieClick = onMovieClick,
                gridState = rememberLazyGridState()
            )
        }
    }
}
