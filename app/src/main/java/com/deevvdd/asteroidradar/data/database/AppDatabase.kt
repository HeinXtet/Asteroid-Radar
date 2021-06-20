package com.deevvdd.asteroidradar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.domain.model.PictureOfDay
import com.deevvdd.asteroidradar.utils.Converters

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
@Database(
    entities = [
        PictureOfDay::class,
        Asteroid::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao
}
