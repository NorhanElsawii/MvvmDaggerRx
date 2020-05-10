package com.example.mvvmdaggerrx.utils

import com.example.mvvmdaggerrx.data.remote.ErrorResponse

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
sealed class Status {
    data class Success<T>(
        var data: T?
    ) : Status()

    object SuccessLoadingMore : Status()

    data class Error(
        var errorResponse: ErrorResponse
    ) : Status()

    data class ErrorLoadingMore(var errorResponse: ErrorResponse) : Status()

    object Loading : Status()

    object LoadingMore : Status()
}