package com.everything.deckapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.everything.deckapp.data.models.Card
import com.everything.deckapp.data.remote.ApiHelper
import kotlinx.coroutines.launch
import com.everything.deckapp.data.remote.Result


class JsonViewModel(application: Application) : AndroidViewModel(application) {
    val jsonData: MutableLiveData<Card> by lazy {
        MutableLiveData<Card>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getDeckRequest(key: String, secretKey: String = "") {
        viewModelScope.launch {
            when (val retrofitJson = ApiHelper.getJsonFromURL(key, secretKey)) {
                is Result.Success -> {
                    jsonData.postValue(retrofitJson.data)
                }
                is Result.Error -> {
                    errorMessage.postValue(retrofitJson.exception.second)
                }
            }
        }

    }
}