package com.swvl.assessmenttest.api.db.converter


import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.swvl.assessmenttest.api.models.MoviesListResponseModel


class MoviesTypeConverter {
    @TypeConverter
    fun stringToMovies(json: String): MoviesListResponseModel {
        val gson = Gson()
        val type = object : TypeToken<MoviesListResponseModel>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun moviesToString(list: MoviesListResponseModel): String {
        val gson = Gson()
        val type = object : TypeToken<MoviesListResponseModel>() {}.type
        return gson.toJson(list, type)
    }
}
