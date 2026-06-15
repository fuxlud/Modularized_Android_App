package com.example.movies.ui.screens.popularmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.movies.data.MovieRepository
import kotlinx.coroutines.launch

@Composable
fun PopularMoviesScreen(
    modifier: Modifier = Modifier,
    repository: MovieRepository = remember { MovieRepository() }
) {
    var state by remember { mutableStateOf<PopularMoviesUiState>(PopularMoviesUiState.Loading) }
    var nextPage by remember { mutableIntStateOf(1) }
    var canLoadMore by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(repository) {
        state = runCatching { repository.getPopularMovies(page = 1) }
            .fold(
                onSuccess = { movies ->
                    nextPage = 2
                    canLoadMore = movies.isNotEmpty()
                    PopularMoviesUiState.Loaded(movies)
                },
                onFailure = { error ->
                    PopularMoviesUiState.Error(
                        error.message ?: "Could not load popular movies."
                    )
                }
            )
    }

    PopularMoviesContent(
        state = state,
        modifier = modifier,
        onLoadNextPage = {
            val loadedState = state as? PopularMoviesUiState.Loaded ?: return@PopularMoviesContent
            if (loadedState.isLoadingNextPage || !canLoadMore) return@PopularMoviesContent

            state = loadedState.copy(isLoadingNextPage = true)

            scope.launch {
                state = runCatching { repository.getPopularMovies(page = nextPage) }
                    .fold(
                        onSuccess = { movies ->
                            nextPage += 1
                            canLoadMore = movies.isNotEmpty()
                            loadedState.copy(
                                movies = (loadedState.movies + movies).distinctBy { it.id },
                                isLoadingNextPage = false
                            )
                        },
                        onFailure = {
                            loadedState.copy(isLoadingNextPage = false)
                        }
                    )
            }
        }
    )
}
