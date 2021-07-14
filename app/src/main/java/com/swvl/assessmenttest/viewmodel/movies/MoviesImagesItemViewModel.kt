package com.swvl.assessmenttest.viewmodel.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MoviesImagesItemViewModel(
) : ViewModel() {

    private val _imagesUrl = MutableLiveData<String>()
    val photo = _imagesUrl.map { it }

    fun bind(item: String) {
        _imagesUrl.value = item
    }
}