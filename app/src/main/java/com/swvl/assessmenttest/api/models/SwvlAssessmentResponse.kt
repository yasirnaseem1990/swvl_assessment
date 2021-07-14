package com.swvl.assessmenttest.api.models


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(call: suspend () -> T): SwvlAssessmentResponse<T> = withContext(Dispatchers.IO) {
    try {
        SwvlAssessmentResponse.Success(call.invoke())
    } catch (exception: Exception) {
        SwvlAssessmentResponse.Error(exception, exception.message)
    }
}

sealed class SwvlAssessmentResponse<out R> {
    data class Success<out T>(val data: T) : SwvlAssessmentResponse<T>()
    data class Error(val exception: Exception, val message: String? = null) :
        SwvlAssessmentResponse<Nothing>()

}

val SwvlAssessmentResponse<*>.succeeded
    get() = this is SwvlAssessmentResponse.Success<*>

inline fun <T> SwvlAssessmentResponse<T>.onSuccess(
    crossinline callback: (value: T) -> Unit
): SwvlAssessmentResponse<T> {
    if (succeeded) {
        callback((this as SwvlAssessmentResponse.Success<T>).data)
    }
    return this
}

inline fun <T> SwvlAssessmentResponse<T>.onError(
    crossinline callback: (value: SwvlAssessmentResponse.Error) -> Unit
): SwvlAssessmentResponse<T> {
    if (!succeeded) {
        callback(this as SwvlAssessmentResponse.Error)
    }
    return this
}

