package com.example.movies.ui.screens.popularmovies

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.movies.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class PopularMoviesState(
    val gridState: LazyGridState,
    private val repository: MovieRepository,
    private val scope: CoroutineScope
) {
    var uiState by mutableStateOf<PopularMoviesUiState>(PopularMoviesUiState.Loading)
        private set

    private var nextPage by mutableIntStateOf(1)
    private var canLoadMore by mutableStateOf(true)
    private var didRequestInitialPage by mutableStateOf(false)

    fun loadInitialPage(defaultErrorMessage: String) {
        if (didRequestInitialPage) return

        didRequestInitialPage = true
        scope.launch {
            uiState = runCatching { repository.getPopularMovies(page = 1) }
                .fold(
                    onSuccess = { movies ->
                        nextPage = 2
                        canLoadMore = movies.isNotEmpty()
                        PopularMoviesUiState.Loaded(movies)
                    },
                    onFailure = { error ->
                        PopularMoviesUiState.Error(
                            error.message ?: defaultErrorMessage
                        )
                    }
                )
        }
    }

    fun loadNextPage() {
        val loadedState = uiState as? PopularMoviesUiState.Loaded ?: return
        if (loadedState.isLoadingNextPage || !canLoadMore) return

        uiState = loadedState.copy(isLoadingNextPage = true)

        scope.launch {
            uiState = runCatching { repository.getPopularMovies(page = nextPage) }
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
}

@Composable
fun rememberPopularMoviesState(
    repository: MovieRepository = remember { MovieRepository() }
): PopularMoviesState {
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    return remember(repository, scope, gridState) {
        PopularMoviesState(
            gridState = gridState,
            repository = repository,
            scope = scope
        )
    }
}
