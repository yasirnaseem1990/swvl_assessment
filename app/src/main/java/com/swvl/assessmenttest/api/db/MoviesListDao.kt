package com.swvl.assessmenttest.api.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swvl.assessmenttest.api.models.MoviesListResponseModel
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy

@Dao
interface MoviesListDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<Movy>)

    @Query("SELECT * FROM movies")
    fun findAll(): List<Movy>

    @Query("SELECT COUNT(*) FROM movies")
    fun numberOfRecordsInMovies(): Int

    @Query("SELECT * FROM movies where title  LIKE '%' || :search || '%'  order by year DESC, rating DESC")
    fun filterMovies(search: String?): List<Movy>
}