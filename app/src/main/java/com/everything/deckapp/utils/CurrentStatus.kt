package com.everything.deckapp.utils

sealed class CurrentStatus {
    object Loaded : CurrentStatus()
    object Loading: CurrentStatus()
    data class Error(val exception: String) : CurrentStatus()
}