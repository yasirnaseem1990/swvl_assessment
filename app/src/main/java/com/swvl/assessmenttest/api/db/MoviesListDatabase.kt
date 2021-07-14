package com.swvl.assessmenttest.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swvl.assessmenttest.api.db.converter.Converters
import com.swvl.assessmenttest.api.db.converter.MoviesTypeConverter
import com.swvl.assessmenttest.api.models.MoviesListResponseModel

@Database(
    entities = [MoviesListResponseModel.Movy::class],
    version = 1, exportSchema = false
)

@TypeConverters(
    Converters::class,
    MoviesTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesListDao: MoviesListDao
}