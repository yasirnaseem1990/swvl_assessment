package com.swvl.assessmenttest.viewmodel.movies

import androidx.lifecycle.*
import com.swvl.assessmenttest.api.models.MoviesListResponseModel.Movy
import com.swvl.assessmenttest.api.models.onError
import com.swvl.assessmenttest.api.models.onSuccess
import com.swvl.assessmenttest.repository.MoviesRepository
import com.swvl.assessmenttest.utils.SingleLiveEvent
import com.swvl.assessmenttest.utils.asLiveData
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(), LifecycleObserver {

    val errorOccurred = SingleLiveEvent<String>()
    val movieDetails = SingleLiveEvent<Movy>()

    private val _moviesList = MutableLiveData<List<Movy>>()
    val moviesList = _moviesList.asLiveData()

    private var totalMovieListItems = mutableListOf<Movy>()

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading.asLiveData()

    val querySearchValue = MutableLiveData<String?>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        resetMoviesSetUp()
        getMoviesList()
    }

    fun getMoviesList() {
        viewModelScope.launch {
            if (totalMovieListItems.isEmpty()) {
                _showLoading.value = true
            }
            getMarketRepository()
                .onSuccess {
                    totalMovieListItems.addAll(it)
                    _moviesList.value = totalMovieListItems
                }
                .onError { error ->
                    Timber.e(error.message)
                    error.message.let {
                        errorOccurred.value = it
                    }
                }

            if (_showLoading.value == true) {
                _showLoading.value = false
            }
        }
    }
    private suspend fun getMarketRepository() = when {
            querySearchValue.value?.isNotBlank() == true -> moviesRepository.filterMoviesList(querySearchValue.value)
            else -> moviesRepository.getMoviesList()
        }

    fun onClearFilterClicked() {
        querySearchValue.value = null
        resetMoviesSetUp()
        getMoviesList()
    }

    fun resetMoviesSetUp() {
        totalMovieListItems.clear()
    }

    /**
     * Call this method inside the onViewCreated() when got error
     */
    fun getErrorResult(): LiveData<String> = errorOccurred

    fun getShowLoadingResult(): LiveData<Boolean> = showLoading
}