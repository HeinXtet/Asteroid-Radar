package com.deevvdd.asteroidradar.utils

import androidx.room.TypeConverter
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by heinhtet deevvdd@gmail.com on 20,June,2021
 */
object Converters {
    @TypeConverter
    fun fromString(value: String): ArrayList<Asteroid> {
        val listType: Type = object : TypeToken<ArrayList<Asteroid>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Asteroid>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}