package com.everything.deckapp.viewModels

import android.app.Application
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.everything.deckapp.R
import com.everything.deckapp.data.models.Card
import com.everything.deckapp.data.models.UrlInfoRequest
import com.everything.deckapp.data.remote.ApiHelper
import kotlinx.coroutines.launch
import com.everything.deckapp.data.remote.Result
import com.everything.deckapp.utils.Algorithm
import com.everything.deckapp.utils.CurrentStatus
import java.util.*


class JsonViewModel(application: Application) : AndroidViewModel(application) {

    // The current answer by the Algorithm
    private val _currentAnswer = MutableLiveData<String>()
    val currentAnswer: LiveData<String>
        get() = _currentAnswer

    // The current answer by the text passed
    private val _obtainedAnswer = MutableLiveData<String>()
    val obtainedAnswer: LiveData<String>
        get() = _obtainedAnswer

    // The current status of the test
    private val _currentStatus = MutableLiveData<String>()
    val currentStatus: LiveData<String>
        get() = _currentStatus

    // The current status of the test in boolean state
    private val _currentStatusBoolean = MutableLiveData<Boolean>()
    val currentStatusBoolean: LiveData<Boolean>
        get() = _currentStatusBoolean

    // Tells the UI what it should display in each step
    val shouldUpdateUI: MutableLiveData<CurrentStatus> by lazy {
        MutableLiveData<CurrentStatus>()
    }

    /**
     * Create an instance of [UrlInfoRequest] using [getUrlInfoRequest] and is sent to [ApiHelper] to retrieve Json info as [Card]
     * @param text The text given by the input
     */
    fun getDeckRequest(text: String) {
        val urlInfoRequest = getUrlInfoRequest(text)

        shouldUpdateUI.value = CurrentStatus.Loading
        viewModelScope.launch {
            when (val retrofitJson =
                urlInfoRequest?.url?.let {
                    ApiHelper.getJsonFromURL(
                        it,
                        urlInfoRequest.secretKey
                    )
                }) {
                is Result.Success -> {
                    shouldUpdateUI.value = CurrentStatus.Loaded

                    val answer = Algorithm.getAllDecksWithCompleteSize(retrofitJson.data)

                    _currentAnswer.value = answer.toString()
                    _obtainedAnswer.value = urlInfoRequest.answer ?: "No data"

                    if (urlInfoRequest.answer == answer.toString()) {
                        _currentStatusBoolean.value = true
                        _currentStatus.value =
                            getApplication<Application>().getString(R.string.correct_answer)
                    } else {
                        _currentStatusBoolean.value = false
                        _currentStatus.value =
                            getApplication<Application>().getString(R.string.incorrect_answer)
                    }
                }
                is Result.Error -> {
                    shouldUpdateUI.value = CurrentStatus.Error(retrofitJson.exception.second)

                    clearData()
                }
            }
        }

    }

    /**
     * Clear all the current data
     */
    private fun clearData() {
        _currentAnswer.value = "No data"
        _obtainedAnswer.value = "No data"
        _currentStatus.value =
            "No data"
    }

    /**
     * @param text The text given by the input
     */
    private fun getUrlInfoRequest(text: String): UrlInfoRequest? {
        val urlInfoRequest = UrlInfoRequest()

        // search for the answer if exists
        if (text.lowercase(Locale.ROOT).contains("ans")) {
            var startIndex = text.lowercase(Locale.ROOT).indexOf("ans")
            val answer = text.substring(startIndex + 3, startIndex + 4)
            print(answer)

            urlInfoRequest.answer = answer

            val url = text
                .split("https?:////(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)")

            if (url.isNotEmpty()) {
                urlInfoRequest.url = url[0].substring(url[0].indexOf("b/") + 2)

                startIndex = text.lowercase(Locale.ROOT).indexOf("secret-key:")
                if (startIndex != -1) {
                    startIndex += 12
                    val secretKey = text.substring(
                        startIndex,
                        text.lowercase(Locale.ROOT).lastIndexOf("\")")
                    ).trim()

                    urlInfoRequest.secretKey = secretKey
                }

                return urlInfoRequest
            } else {
                return null
            }
        }

        return null
    }
}