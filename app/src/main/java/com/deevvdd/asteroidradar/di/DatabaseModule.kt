package com.deevvdd.asteroidradar.di

import android.content.Context
import androidx.room.Room
import com.deevvdd.asteroidradar.data.database.AppDatabase
import com.deevvdd.asteroidradar.data.database.AsteroidDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext application: Context,
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "AsteroidRadar.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideAsteroidDao(appDatabase: AppDatabase): AsteroidDao {
        return appDatabase.asteroidDao()
    }
}
