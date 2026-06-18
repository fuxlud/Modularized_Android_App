package com.example.movies.data.di

import com.example.movies.data.remote.MoviesApi
import com.example.movies.data.repositories.TmdbMoviesRepository
import com.example.movies.domain.repositories.MoviesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val moviesDataModule = module {
    single {
        HttpClient(Android)
    }

    single {
        MoviesApi(client = get())
    }

    single<MoviesRepository> {
        TmdbMoviesRepository(api = get())
    }
}
