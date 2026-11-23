package com.example.zad1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PromptActivity : AppCompatActivity() {
    private var correctAnswer: Boolean = false

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prompt)

        correctAnswer = intent.getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, false) //

        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton.setOnClickListener {
            val answerResId = if (correctAnswer) R.string.button_true else R.string.button_false
            answerTextView.setText(answerResId)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(answerShownResult: Boolean) {
        val resultIntent = Intent().apply {
            putExtra(KEY_EXTRA_ANSWER_SHOWN, answerShownResult)
        }
        setResult(Activity.RESULT_OK, resultIntent)
    }

    companion object {
        const val KEY_EXTRA_ANSWER_SHOWN = "com.example.zad1.answer_shown"
    }
}