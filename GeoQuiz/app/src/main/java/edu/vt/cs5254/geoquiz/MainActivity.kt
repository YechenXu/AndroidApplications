package edu.vt.cs5254.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Name: Yechen Xu
    // PID: yechenx

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener {
            Snackbar.make(it, R.string.correct_result, Snackbar.LENGTH_SHORT).show()
        }
        falseButton.setOnClickListener {
            Snackbar.make(it, R.string.incorrect_result, Snackbar.LENGTH_SHORT).show()
        }
    }
}