package com.example.movies.data.local.favorites

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}
