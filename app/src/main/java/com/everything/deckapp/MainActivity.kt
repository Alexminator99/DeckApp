package com.everything.deckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.models.Card
import com.google.gson.Gson
import java.net.URL

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
    }

    private fun getAllCardsFromJsonAndRunAlgorithm() : Int {
        val cardsArray = ArrayList<Card>()

        cardsArray.addAll(Gson().fromJson(cardsDeck, Array<Card>::class.java))
        return getAllDecksWithCompleteSize(cardsArray)
    }

    private fun getAllDecksWithCompleteSize(cardsArray: ArrayList<Card>): Int {
        val deck = ArrayList<ArrayList<Card>>()

        typesOfCards.forEach { cardType: String ->
            val listOfCardType = ArrayList<Card>()
            cardsArray.map {
                if (cardType == it.suit)
                    listOfCardType.add(it)
            }

            deck.add(listOfCardType)
        }

        val entireCardsForAType = ArrayList<Int>()

        deck.forEach {
            entireCardsForAType.add(getQuantityOfAllTypesOfCard(it))
        }

        entireCardsForAType.sort()
        return entireCardsForAType[0]   // The answer is the smallest amount of deck card that can be possible made
    }

    private fun getQuantityOfAllTypesOfCard(arrayOfTypeCard: ArrayList<Card>): Int {

        val cardsSums = ArrayList<Int>()
        repeat(14){
            cardsSums.add(0)
        }

        arrayOfTypeCard.forEach {
            if (it.value.toIntOrNull() == null)
                cardsSums[specialCardsValue[it.value]!!]++      // We make here a check of course but we know is not null
            else cardsSums[it.value.toInt()]++
        }

        cardsSums.sort()
        return cardsSums[1]
    }

    companion object {
        private val typesOfCards = arrayOf("clubs", "spades", "diamonds", "hearts")

        private val specialCardsValue = HashMap<String, Int>()
    }

    class CardsComparator : Comparator<Card> {
        override fun compare(o1: Card?, o2: Card?): Int {
            if (o1 == null || o2 == null)
                return 0
            if (o1.suit.compareTo(o2.suit) == 0)
                return o1.value.compareTo(o2.value)
            return o1.suit.compareTo(o2.suit)
        }

    }
}

