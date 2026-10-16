package com.example.zad1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private var correctAnswersCount = 0

    private val questions: Array<Question> = arrayOf(
        Question(R.string.q1, trueAnswer = true),
        Question(R.string.q2, trueAnswer = false),
        Question(R.string.q3, trueAnswer = false),
        Question(R.string.q4, trueAnswer = true),
        Question(R.string.q5, trueAnswer = true)
    )
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener {
            checkAnswerCorrectness(true)
        }
        falseButton.setOnClickListener {
            checkAnswerCorrectness(false)
        }

        nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
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

    private fun setNextQuestion() {
        questionTextView.setText(questions[currentQuestionIndex].questionId)
    }

    private fun checkAnswerCorrectness(userAnswer: Boolean) {
        val correctAnswer = questions[currentQuestionIndex].trueAnswer
        val messageId: Int
        if (userAnswer == correctAnswer) {
            messageId = R.string.correct_answer
            correctAnswersCount++
        } else {
            messageId = R.string.incorrect_answer
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }
}