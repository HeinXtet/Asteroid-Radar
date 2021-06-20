package com.deevvdd.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import com.deevvdd.asteroidradar.api.*
import com.deevvdd.asteroidradar.data.database.AsteroidDao
import com.deevvdd.asteroidradar.domain.mapper.AsteroidMapper.toDomain
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.domain.model.PictureOfDay
import com.deevvdd.asteroidradar.domain.repository.AsteroidRepository
import com.deevvdd.asteroidradar.domain.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
class AsteroidRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val asteroidDao: AsteroidDao,
) : AsteroidRepository {
    override suspend fun fetchImageOfTheDay(): Either<ErrorException, PictureOfDay> {
        return ApiHandling.handleApiCall(
            apiCall = { apiService.fetchImageOfTheDay() },
            mapper = {
                val result = it.toDomain()
                savePictureOfDay(result)
                result
            })
    }

    override fun getPictureOfDay(): LiveData<PictureOfDay?> = asteroidDao.getPictureOfDay()


    override suspend fun savePictureOfDay(pictureOfDay: PictureOfDay) =
        withContext(Dispatchers.IO) {
            asteroidDao.insertPictureOfDay(pictureOfDay)
        }

    override suspend fun saveAllAsteroidData(asteroid: List<Asteroid>) =
        withContext(Dispatchers.IO) {
            asteroidDao.insertAllAsteroid(asteroid)
        }

    override fun getAsteroidWithClosetDate(
        startDate: String,
        endDate: String
    ): LiveData<List<Asteroid>> =
        asteroidDao.getAsteroidWithClosetDate(startDate, endDate)

    override suspend fun fetchAsteroidData(
        startDate: String,
        endDate: String
    ): Either<ErrorException, List<Asteroid>> {
        return ApiHandling.handleApiCall(
            apiCall = { apiService.fetchAsteroidData(startDate, endDate) },
            mapper = {
                val result = JSONObject(it).toDomain()
                saveAllAsteroidData(result)
                result
            })
    }
    override fun getAllSavedAsteroid() = asteroidDao.getAllSavedAsteroids()
    override suspend fun deleteAsteroidBeyondToday(date: String){
        withContext(Dispatchers.IO) {
            asteroidDao.deleteAsteroidDataBeyondToday(date)
        }
    }
}