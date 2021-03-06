package com.everything.deckapp.data.remote

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Pair<Int, String>) : Result<Nothing>()
}