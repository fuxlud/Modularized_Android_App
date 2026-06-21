package com.example.movies.data.local.favorites.di

import androidx.room.Room
import com.example.movies.data.local.favorites.FavoriteMoviesDatabase
import com.example.movies.data.local.favorites.FavoriteMoviesStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val favoritesDataModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = FavoriteMoviesDatabase::class.java,
            name = "favorite_movies.db"
        ).build()
    }
    single {
        get<FavoriteMoviesDatabase>().favoriteMoviesDao()
    }
    single {
        FavoriteMoviesStore(favoriteMoviesDao = get())
    }
}
