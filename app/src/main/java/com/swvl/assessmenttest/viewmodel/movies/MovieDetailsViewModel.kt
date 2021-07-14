package com.swvl.assessmenttest.viewmodel.movies

import androidx.lifecycle.*
import com.google.gson.Gson
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.api.models.SearchedMoviePicturesResponseModel
import com.swvl.assessmenttest.api.models.onError
import com.swvl.assessmenttest.api.models.onSuccess
import com.swvl.assessmenttest.repository.MoviesPictureRepository
import com.swvl.assessmenttest.utils.SingleLiveEvent
import com.swvl.assessmenttest.utils.asLiveData
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailsViewModel(
    private val modelObj: String,
    private val moviePictureRepository: MoviesPictureRepository
) : ViewModel(), LifecycleObserver {

    private val showLoading = MutableLiveData<Boolean>()
    private val _searchedMoviesPictureModel = MutableLiveData<SearchedMoviePicturesResponseModel>()
    private var page = FIRST_PAGE

    val errorOccurred = SingleLiveEvent<String>()
    val moviesModel = Gson().fromJson(modelObj, Movy::class.java)

    val searchMoviePictureData = _searchedMoviesPictureModel.asLiveData()

    /*val movieTitle = movieDetailData.map { it.title }
    val movieYear  = movieDetailData.map { it.year.toString() }*/

    val movieTitle = moviesModel.title
    val movieYear  = moviesModel.year.toString()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        getSearchedMoviePicture(moviesModel.title)
    }


    private fun getSearchedMoviePicture(movieTitle: String?) {
        viewModelScope.launch {
            showLoading.value = true
            moviePictureRepository
                .getSearchedMoviePictures(movieTitle, page)
                .onSuccess {
                    _searchedMoviesPictureModel.value = it
                }
                .onError { error ->
                    Timber.e(error.message)
                    error.message.let {
                        errorOccurred.value = it
                    }
                }

            /*moviePictureRepository.getPlayerDetail(userSession.sport, playerId)
                .onSuccess {
                    _moviesDetailsModel.value = it
                }
                .onError { error ->
                    Timber.e(error.message)
                    error.message.let {
                        errorOccurred.value = it
                    }
                }*/

            showLoading.value = false
        }
    }

    /*private fun getSearchedMoviePictureUrl(movieTitle: String?) {
        viewModelScope.launch {


            moviePictureRepository.getPlayerDetail(userSession.sport, playerId)
                .onSuccess {
                    _moviesDetailsModel.value = it
                }
                .onError { error ->
                    Timber.e(error.message)
                    error.message.let {
                        errorOccurred.value = it
                    }
                }

            showLoading.value = false
        }
    }*/

    /**
     * Call this method inside the onViewCreated() when got error
     */
    fun getErrorResult(): LiveData<String> = errorOccurred

    fun getShowLoadingResult(): LiveData<Boolean> = showLoading

    companion object {
        private const val FIRST_PAGE = 1
        private const val ITEMS_BEFORE_LOADING_MORE = 4
    }

}