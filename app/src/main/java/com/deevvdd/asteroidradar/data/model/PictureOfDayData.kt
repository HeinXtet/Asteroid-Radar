package com.deevvdd.asteroidradar.data.model

import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.Json

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */


data class PictureOfDayData(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "media_type")
    val media_type: String
)