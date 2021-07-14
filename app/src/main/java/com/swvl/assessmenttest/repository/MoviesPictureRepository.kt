package com.swvl.assessmenttest.repository

import com.swvl.assessmenttest.api.endpoints.MoviesPicturesEndPoint
import com.swvl.assessmenttest.api.models.SearchedMoviePicturesResponseModel
import com.swvl.assessmenttest.api.models.SwvlAssessmentResponse
import com.swvl.assessmenttest.api.models.safeApiCall

class MoviesPictureRepository(
    private val api: MoviesPicturesEndPoint
) {

    suspend fun getSearchedMoviePictures(
        search: String?,
        page: Int,
    ): SwvlAssessmentResponse<SearchedMoviePicturesResponseModel> {
        return safeApiCall { api.getSearchedMoviePictures(search, page, PAGINATION_PAGE_SIZE) }
    }

    companion object {
        private const val PAGINATION_PAGE_SIZE = 30
    }

}