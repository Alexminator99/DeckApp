package com.everything.deckapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.everything.deckapp.data.models.CardItem
import com.everything.deckapp.data.models.UrlInfoRequest
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.viewModels.JsonViewModel
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urlInfoRequest = UrlInfoRequest()

    private val viewModel: JsonViewModel by lazy {
        ViewModelProvider(this).get(JsonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillSpecialCards()

        initViewModel()
        init()
    }

    private fun initViewModel() {
        viewModel.jsonData.observe(this) {
            it.forEach { cardItem ->
                cardItem.toString()
            }

            val answer = getAllDecksWithCompleteSize(it)

            binding.relativeLayoutLoading.visibility = View.GONE

            binding.answerLayout.visibility = View.VISIBLE

            binding.textViewAnswerAlgorithm.text = answer.toString()
            binding.textViewAnswerDecodedText.text = urlInfoRequest.answer

            if (urlInfoRequest.answer != null && urlInfoRequest.answer == answer.toString()) {

                binding.textViewStatusAnswer.text = getString(R.string.correct_answer)
            } else {
                binding.textViewStatusAnswer.text = getString(R.string.incorrect_answer)
            }
        }
        viewModel.errorMessage.observe(this) {
            Log.e("ERROR", it)
            binding.answerCardLayout.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        binding.materialButtonBeginTest.setOnClickListener {
            urlInfoRequest.clear()

            binding.answerCardLayout.visibility = View.VISIBLE
            binding.relativeLayoutLoading.visibility = View.VISIBLE

            binding.answerLayout.visibility = View.GONE
            binding.progressIndicator.show()

            if (binding.textInputEditTextUrl.text?.isNotEmpty() == true) {
                val text = binding.textInputEditTextUrl.text.toString()

                // search for the answer if exists
                if (text.lowercase(Locale.ROOT).contains("ans")) {
                    var startIndex = text.lowercase(Locale.ROOT).indexOf("ans")
                    val answer = text.substring(startIndex + 3, startIndex + 4)
                    print(answer)

                    urlInfoRequest.answer = answer

                    val url = binding.textInputEditTextUrl.text.toString()
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
                        viewModel.getDeckRequest(urlInfoRequest)
                        Toast.makeText(
                            this,
                            "Url is correct, loading answer...",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(this, "Please paste a valid URL", Toast.LENGTH_SHORT).show()
                        binding.answerCardLayout.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this, "Please paste a valid URL", Toast.LENGTH_SHORT).show()
                binding.answerCardLayout.visibility = View.GONE
            }
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

