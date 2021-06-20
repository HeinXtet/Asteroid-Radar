package com.deevvdd.asteroidradar.di

import com.deevvdd.asteroidradar.data.repository.AsteroidRepositoryImpl
import com.deevvdd.asteroidradar.domain.repository.AsteroidRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAsteroidRepository(
        remoteDataSourceImpl: AsteroidRepositoryImpl
    ): AsteroidRepository
}
