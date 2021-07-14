package com.swvl.assessmenttest.repository

import android.content.Context
import com.swvl.assessmenttest.api.db.MoviesListDao
import com.swvl.assessmenttest.api.db.converter.MoviesTypeConverter
import com.swvl.assessmenttest.api.models.MoviesListResponseModel
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.api.models.SwvlAssessmentResponse
import com.swvl.assessmenttest.api.models.safeApiCall
import com.swvl.assessmenttest.utils.ReadFileFromAssets



class MoviesRepository(
    private val context: Context,
    private val dao: MoviesListDao
) {
    lateinit var moviesJson: String
    var converters: MoviesTypeConverter = MoviesTypeConverter()



    suspend fun addMoviesList(
    ): SwvlAssessmentResponse<MoviesListResponseModel> {
        return safeApiCall {
            moviesJson = ReadFileFromAssets.getJsonFromAssets(context)
            converters = MoviesTypeConverter()
            if (dao.numberOfRecordsInMovies() <= 0) {
                dao.add(converters.stringToMovies(moviesJson).movies)
            }
            converters.stringToMovies(moviesJson)
        }
    }

    suspend fun getMoviesList(
    ): SwvlAssessmentResponse<List<Movy>> {
        return safeApiCall {
            dao.findAll()
        }
    }

    suspend fun filterMoviesList(
        search: String?
    ): SwvlAssessmentResponse<List<Movy>> {
        return safeApiCall {
            dao.filterMovies(search)
        }
    }

}