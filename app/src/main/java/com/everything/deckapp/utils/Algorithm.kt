package com.everything.deckapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.everything.deckapp.MainActivity
import com.everything.deckapp.data.models.CardItem
import com.everything.deckapp.data.models.UrlInfoRequest
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

class Algorithm {
    companion object {
        private val typesOfCards = HashMap<String, Int>()

        private val specialCardsValue = HashMap<String, Int>()

        private fun fillSpecialCards() {
            specialCardsValue["A"] = 1
            specialCardsValue["J"] = 11
            specialCardsValue["Q"] = 12
            specialCardsValue["K"] = 13

            typesOfCards["clubs"] = 1
            typesOfCards["diamonds"] = 2
            typesOfCards["spades"] = 3
            typesOfCards["hearts"] = 4

        }

        fun getAllDecksWithCompleteSize(cardsArray: ArrayList<CardItem>): Int {
            fillSpecialCards()

            val deck = Array(5) { IntArray(14) }

            repeat(cardsArray.size) {
                deck[typesOfCards[cardsArray[it].suit]!!][if (cardsArray[it].value.toIntOrNull() == null)
                    specialCardsValue[cardsArray[it].value]!!.toInt()
                else cardsArray[it].value.toInt()]++
            }

            var result = 9999999

            for (i in 1..4) {
                deck[i].sort()
                result = min(result, deck[i][1])
            }

            return result
        }

        fun getUrlInfoRequest(text: String): UrlInfoRequest? {
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
}