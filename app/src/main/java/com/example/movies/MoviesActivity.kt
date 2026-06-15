package com.example.movies

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movies.data.local.FavoriteMoviesStore
import com.example.movies.ui.screens.movies.MoviesHomeScreen
import com.example.movies.ui.theme.MoviesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent {
            MoviesTheme(darkTheme = true) {
                val favoritesStore = remember {
                    FavoriteMoviesStore(applicationContext)
                }
                var favoriteMovies by remember {
                    mutableStateOf(favoritesStore.getMovies())
                }
                val scope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFF11263C)
                ) { innerPadding ->
                    MoviesHomeScreen(
                        favoriteMovies = favoriteMovies,
                        onFavoriteClick = { movie ->
                            scope.launch {
                                favoriteMovies = withContext(Dispatchers.IO) {
                                    favoritesStore.toggleMovie(movie)
                                }
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
