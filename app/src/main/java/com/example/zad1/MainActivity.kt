package com.example.zad1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var hintButton: Button
    private lateinit var nextButton: Button

    private var correctAnswersCount = 0
    private var currentQuestionIndex = 0
    private var answerWasShown = false

    private val questions: Array<Question> = arrayOf(
        Question(R.string.q1, trueAnswer = true),
        Question(R.string.q2, trueAnswer = false),
        Question(R.string.q3, trueAnswer = false),
        Question(R.string.q4, trueAnswer = true),
        Question(R.string.q5, trueAnswer = true)
    )

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            answerWasShown = result.data?.getBooleanExtra(
                PromptActivity.KEY_EXTRA_ANSWER_SHOWN,
                false
            ) ?: false
            if (answerWasShown) {
                Log.d(TAG, "answer was shown")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION, 0)
            correctAnswersCount = savedInstanceState.getInt(KEY_CORRECT_ANSWERS_COUNT, 0)
            answerWasShown = savedInstanceState.getBoolean(KEY_ANSWER_WAS_SHOWN, false)
        }

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        hintButton = findViewById(R.id.hint_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener {
            checkAnswerCorrectness(true)
        }

        falseButton.setOnClickListener {
            checkAnswerCorrectness(false)
        }

        hintButton.setOnClickListener {
            val intent = Intent(this, PromptActivity::class.java).apply {
                val correctAnswer = questions[currentQuestionIndex].trueAnswer
                putExtra(KEY_EXTRA_ANSWER, correctAnswer)
            }
            startForResult.launch(intent)
        }

        nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                answerWasShown = false
                setNextQuestion()
            } else {
                val intent = Intent(this, SummaryActivity::class.java).apply {
                    putExtra("SCORE_EXTRA", correctAnswersCount)
                    putExtra("TOTAL_EXTRA", questions.size)
                }
                startActivity(intent)
                finish()
            }
        }

        setNextQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Wywołanie metody onSaveInstanceState, current question $currentQuestionIndex")
        outState.putInt(KEY_CURRENT_QUESTION, currentQuestionIndex)
        outState.putInt(KEY_CORRECT_ANSWERS_COUNT, correctAnswersCount)
        outState.putBoolean(KEY_ANSWER_WAS_SHOWN, answerWasShown)
    }

    private fun setNextQuestion() {
        questionTextView.setText(questions[currentQuestionIndex].questionId)
    }

    private fun checkAnswerCorrectness(userAnswer: Boolean) {
        val correctAnswer = questions[currentQuestionIndex].trueAnswer
        val messageResId: Int = when {
            answerWasShown -> R.string.answer_was_shown
            userAnswer == correctAnswer -> {
                correctAnswersCount++
                R.string.correct_answer
            }
            else -> R.string.incorrect_answer
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show() //[cite: 7]
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_CURRENT_QUESTION = "currentQuestion"
        private const val KEY_CORRECT_ANSWERS_COUNT = "correctAnswersCount"
        private const val KEY_ANSWER_WAS_SHOWN = "answerWasShown"
        const val KEY_EXTRA_ANSWER = "com.example.zad1.correctAnswer"
    }
}