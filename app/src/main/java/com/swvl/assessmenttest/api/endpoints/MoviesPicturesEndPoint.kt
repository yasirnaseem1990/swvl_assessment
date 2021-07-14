package com.swvl.assessmenttest.api.endpoints

import com.swvl.assessmenttest.BuildConfig
import com.swvl.assessmenttest.api.models.SearchedMoviePicturesResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesPicturesEndPoint {

    @GET(BuildConfig.API_URL)
    suspend fun getSearchedMoviePictures(
        @Query("text") text: String?,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): SearchedMoviePicturesResponseModel

    /*@GET("players/{sport}/{playerId}/stats")
    suspend fun playerStats(
        @Path("sport") sport: String,
        @Path("playerId") playerId: String?
    ): PlayerStatsResponse*/



}