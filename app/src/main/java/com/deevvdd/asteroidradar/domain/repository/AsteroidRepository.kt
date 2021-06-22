package com.deevvdd.asteroidradar.domain.repository

import androidx.lifecycle.LiveData
import com.deevvdd.asteroidradar.api.ErrorException
import com.deevvdd.asteroidradar.api.getSevenDayTodayOnwards
import com.deevvdd.asteroidradar.api.getToday
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.domain.model.PictureOfDay
import com.deevvdd.asteroidradar.domain.utils.Either
import kotlinx.coroutines.flow.Flow

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
interface AsteroidRepository {
    suspend fun fetchImageOfTheDay(): Either<ErrorException, PictureOfDay>

    fun getPictureOfDay(): LiveData<PictureOfDay?>

    suspend fun savePictureOfDay(pictureOfDay: PictureOfDay)

    suspend fun saveAllAsteroidData(asteroid: List<Asteroid>)

    fun getAsteroidWithClosetDate(startDate: String, endDate: String): Flow<List<Asteroid>>

    fun getAllSavedAsteroid(): Flow<List<Asteroid>>

    suspend fun fetchAsteroidData(
        startDate: String = getToday(),
        endDate: String = getSevenDayTodayOnwards()
    ): Either<ErrorException, List<Asteroid>>

    suspend fun deleteAsteroidBeyondToday(date: String = getToday())

}