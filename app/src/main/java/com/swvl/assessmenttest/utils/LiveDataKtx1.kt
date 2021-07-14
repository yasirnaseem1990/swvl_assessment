package com.swvl.assessmenttest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <Y, T1, T2> zip(
    t1: LiveData<T1>,
    t2: LiveData<T2>,
    transform: (T1, T2) -> Y
): LiveData<Y> {
    return t1.switchMap { t1Value ->
        t2.map { t2Value ->
            transform(t1Value, t2Value)
        }
    }
}

