package com.swvl.assessmenttest.viewmodel.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.utils.SingleLiveEvent

class MoviesListItemViewModel(
    private val movieDetails: SingleLiveEvent<Movy>,
) : ViewModel() {

    private val movies = MutableLiveData<Movy>()


    val movieTitle = movies.map { it.title }

    val movieYear  = movies.map { it.year.toString() }

    val movieRating = movies.map { it.rating.toString() }


    fun bind(item: Movy) {
        movies.value = item
    }

    fun onItemClicked() {
        movies.value?.let {
            movieDetails.value = it
        }
    }
}