package com.example.movies.application

import android.graphics.Color as AndroidColor
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.movies.data.local.favorites.FavoriteMoviesStore
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.MoviesRepository
import com.example.movies.presentation.designsystem.theme.AppBackgroundTop
import com.example.movies.presentation.designsystem.theme.MoviesTheme
import com.example.movies.presentation.features.main.MoviesHomeScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MoviesActivity : ComponentActivity() {
    private val favoritesStore: FavoriteMoviesStore by inject()
    private val moviesRepository: MoviesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            MoviesTheme(darkTheme = true) {
                var favoriteMovies by remember {
                    mutableStateOf<List<Movie>>(emptyList())
                }
                val scope = rememberCoroutineScope()

                LaunchedEffect(favoritesStore) {
                    favoriteMovies = favoritesStore.getMovies()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = AppBackgroundTop
                ) { innerPadding ->
                    MoviesHomeScreen(
                        favoriteMovies = favoriteMovies,
                        moviesRepository = moviesRepository,
                        onFavoriteClick = { movie ->
                            scope.launch {
                                favoriteMovies = favoritesStore.toggleMovie(movie)
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
