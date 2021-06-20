package com.deevvdd.asteroidradar.api

import com.deevvdd.asteroidradar.data.model.PictureOfDayData
import com.deevvdd.asteroidradar.domain.model.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
interface ApiService {

    @GET("planetary/apod")
    suspend fun fetchImageOfTheDay(): Response<PictureOfDayData>

    @GET("neo/rest/v1/feed")
    suspend fun fetchAsteroidData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<String>
}