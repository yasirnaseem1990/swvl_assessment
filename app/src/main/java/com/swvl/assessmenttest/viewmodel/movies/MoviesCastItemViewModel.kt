package com.swvl.assessmenttest.viewmodel.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.utils.SingleLiveEvent

class MoviesCastItemViewModel(
) : ViewModel() {

    private val movies = MutableLiveData<String>()
    val castTitle = movies.map { it }

    fun bind(item: String) {
        movies.value = item
    }
}