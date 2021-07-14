package com.swvl.assessmenttest.api.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


data class MoviesListResponseModel(
    val movies: List<Movy>
) {
    @Entity(tableName = "movies")
    @Parcelize
    data class Movy(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val cast: List<String>,
        val genres: List<String>,
        val rating: Int,
        val title: String,
        val year: Int
    ): Parcelable
}