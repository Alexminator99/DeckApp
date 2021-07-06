package com.everything.deckapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.everything.deckapp.data.models.CardItem
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.viewModels.JsonViewModel
import kotlin.math.min


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: JsonViewModel by lazy {
        ViewModelProvider(this).get(JsonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillSpecialCards()

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getDeckRequest("60e342b39328b059d7b7801e", secretKey = "\$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG")
        viewModel.jsonData.observe(this) {
            it.forEach { cardItem ->
                cardItem.toString()
            }

            Log.d("RESULT", getAllDecksWithCompleteSize(it).toString())
        }
        viewModel.errorMessage.observe(this){
            Log.e("ERROR", it)
        }
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

    private fun getAllDecksWithCompleteSize(cardsArray: ArrayList<CardItem>): Int {
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

