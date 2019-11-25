package com.example.softartch_lib.component

sealed class RequestDataState<out T> {
    object LoadingShow : RequestDataState<Nothing>()
    object LoadingFinished : RequestDataState<Nothing>()
    object NoInternet : RequestDataState<Nothing>()
    data class Success<out R>(val data: R) : RequestDataState<R>()
    data class Error(val exception: Throwable) : RequestDataState<Nothing>()
}