package com.everything.deckapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.models.Card
import com.google.gson.Gson
import kotlin.math.min


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillSpecialCards()

        Log.d("RESULT", getAllCardsFromJsonAndRunAlgorithm().toString())
    }

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

    private fun getAllCardsFromJsonAndRunAlgorithm(): Int {
        val cardsArray = ArrayList<Card>()

        cardsArray.addAll(Gson().fromJson(cardsDeck, Array<Card>::class.java))
        return getAllDecksWithCompleteSize(cardsArray)
    }

    private fun getAllDecksWithCompleteSize(cardsArray: ArrayList<Card>): Int {
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

    companion object {
        private val typesOfCards = HashMap<String, Int>()

        private val specialCardsValue = HashMap<String, Int>()
    }
}

