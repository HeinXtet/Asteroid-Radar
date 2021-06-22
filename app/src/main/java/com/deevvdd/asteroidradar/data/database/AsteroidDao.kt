package com.deevvdd.asteroidradar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.domain.model.PictureOfDay
import kotlinx.coroutines.flow.Flow

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
@Dao
interface AsteroidDao {

    @Query("SELECT * FROM picture_of_day")
    fun getPictureOfDay(): LiveData<PictureOfDay?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(day: PictureOfDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(data: List<Asteroid>)


    @Query("SELECT * FROM asteroid_data WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidWithClosetDate(startDate: String, endDate: String): Flow<List<Asteroid>>

    @Query("SELECT * FROM asteroid_data ORDER BY closeApproachDate ASC")
    fun getAllSavedAsteroids(): Flow<List<Asteroid>>

    @Query("DELETE FROM asteroid_data WHERE closeApproachDate < :today")
    fun deleteAsteroidDataBeyondToday(today: String): Int
}