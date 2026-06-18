package com.example.movies.presentation.features.popularmovies

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class PopularMoviesState(
    val gridState: LazyGridState,
    private val repository: MoviesRepository,
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
