package com.everything.deckapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.everything.blogexample.utils.gone
import com.everything.blogexample.utils.visible
import com.everything.deckapp.databinding.ActivityMainBinding
import com.everything.deckapp.utils.CurrentStatus
import com.everything.deckapp.viewModels.JsonViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: JsonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initViewModel()
        init()
    }

    private fun initViewModel() {
        viewModel.shouldUpdateUI.observe(this) {
            when (it) {
                is CurrentStatus.Loaded -> {
                    binding.relativeLayoutLoading.gone()
                    binding.answerLayout.visible()
                }
                is CurrentStatus.Loading -> {
                    binding.answerCardLayout.visible()
                    binding.relativeLayoutLoading.visible()

                    binding.answerLayout.gone()
                    binding.progressIndicator.show()
                }
                is CurrentStatus.Error -> {
                    Log.e("ERROR", it.exception)
                    binding.answerCardLayout.gone()
                    Toast.makeText(this, it.exception, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun init() {
        binding.materialButtonBeginTest.setOnClickListener {
            if (binding.textInputEditTextUrl.text?.isNotEmpty() == true) {
                val text = binding.textInputEditTextUrl.text.toString()
                viewModel.getDeckRequest(text)
            } else {
                Toast.makeText(this, "Please paste a valid URL", Toast.LENGTH_SHORT).show()
                binding.answerCardLayout.visibility = View.GONE
            }

        }
    }
}

