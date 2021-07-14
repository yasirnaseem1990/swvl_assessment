package com.swvl.assessmenttest.viewmodel.splash

import androidx.lifecycle.*
import com.swvl.assessmenttest.api.models.MoviesListResponseModel
import com.swvl.assessmenttest.api.models.onError
import com.swvl.assessmenttest.api.models.onSuccess
import com.swvl.assessmenttest.repository.MoviesRepository
import com.swvl.assessmenttest.utils.SingleLiveEvent
import com.swvl.assessmenttest.utils.asLiveData
import kotlinx.coroutines.launch
import timber.log.Timber


class SplashViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(

), LifecycleObserver {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading.asLiveData()
    val errorOccurred = SingleLiveEvent<String?>()
    private val _moviesListModel = MutableLiveData<MoviesListResponseModel>()

    val moviesListData = _moviesListModel.asLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onStart() {
        addMoviesList()
    }

    private fun addMoviesList() {
        viewModelScope.launch {
            _showLoading.value = true
            moviesRepository.addMoviesList()
                .onSuccess {
                    _moviesListModel.value = it
                }
                .onError { error ->
                    Timber.e(error.message)
                    error.message.let {
                        errorOccurred.value = it
                    }
                }
            _showLoading.value = false
        }
    }

}