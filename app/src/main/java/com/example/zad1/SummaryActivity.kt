package com.example.zad1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val summaryTextView = findViewById<TextView>(R.id.summary_text_view)
        val restartButton = findViewById<Button>(R.id.restart_button)

        // Odczytanie przekazanych danych z MainActivity
        val score = intent.getIntExtra("SCORE_EXTRA", 0)
        val total = intent.getIntExtra("TOTAL_EXTRA", 5)

        summaryTextView.text = "Poprawne odpowiedzi: $score/$total"

        // Obsługa przycisku "Spróbuj jeszcze raz"
        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Flagi czyszczące historię ekranów, żeby po powrocie zamknąć SummaryActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}