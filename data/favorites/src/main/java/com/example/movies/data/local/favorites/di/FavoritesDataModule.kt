package com.example.movies.data.local.favorites.di

import com.example.movies.data.local.favorites.FavoriteMoviesStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val favoritesDataModule = module {
    single {
        FavoriteMoviesStore(context = androidContext())
    }
}
