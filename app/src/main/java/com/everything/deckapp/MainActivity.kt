package com.everything.deckapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.everything.deckapp.data.models.UrlInfoRequest
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.utils.Algorithm
import com.everything.deckapp.viewModels.JsonViewModel

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

        initViewModel()
        init()
    }

    private fun initViewModel() {
        viewModel.jsonData.observe(this) {
            it.forEach { cardItem ->
                cardItem.toString()
            }

            val answer = Algorithm.getAllDecksWithCompleteSize(it)

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
                val urlInfoRequest = Algorithm.getUrlInfoRequest(text)

                if (urlInfoRequest != null) {
                    Toast.makeText(
                        this,
                        "Url is correct, loading answer...",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    viewModel.getDeckRequest(urlInfoRequest)
                } else {
                    Toast.makeText(this, "Please paste a valid URL", Toast.LENGTH_SHORT).show()
                    binding.answerCardLayout.visibility = View.GONE
                }

            } else {
                Toast.makeText(this, "Please paste a valid URL", Toast.LENGTH_SHORT).show()
                binding.answerCardLayout.visibility = View.GONE
            }
        }
    }


}

