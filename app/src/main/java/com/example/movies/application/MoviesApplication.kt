package com.example.movies.application

import android.app.Application
import com.example.movies.data.di.moviesDataModule
import com.example.movies.data.local.favorites.di.favoritesDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(
                moviesDataModule,
                favoritesDataModule
            )
        }
    }
}
