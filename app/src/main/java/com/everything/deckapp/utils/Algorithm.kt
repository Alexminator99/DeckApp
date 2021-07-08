package com.everything.deckapp.utils

import com.everything.deckapp.data.models.CardItem
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
                deck[typesOfCards[cardsArray[it].suit]!!][getColumnByValue(cardsArray[it].value)]++
                deck[typesOfCards[cardsArray[it].suit]!!][0] = 999999
            }

            var result = 9999999

            for (i in 1..4) {
                val minimum = deck[i].minOrNull()
                if (minimum != null)
                    result = min(result, minimum)
            }

            return result
        }

        private fun getColumnByValue(value: String): Int {
            return if (value.toIntOrNull() == null)
                specialCardsValue[value] ?: 0 // should never return 0
            else value.toInt()
        }
    }
}