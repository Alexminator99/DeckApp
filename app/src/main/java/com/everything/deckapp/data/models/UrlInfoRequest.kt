package com.everything.deckapp.data.models

class UrlInfoRequest {
    fun clear() {
        answer = null
        url = null
        secretKey = ""
    }

    var answer: String? = null // Answer given by url text
    var url: String? = null // URL extracted form text
    var secretKey: String = "" // secret key extracted form text
}