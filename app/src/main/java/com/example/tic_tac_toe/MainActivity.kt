package com.example.tic_tac_toe

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var boardButtons: List<Button>
    private var currentIsX = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardButtons = listOf(
            findViewById(R.id.button_01), findViewById(R.id.button_02), findViewById(R.id.button_03),
            findViewById(R.id.button_11), findViewById(R.id.button_12), findViewById(R.id.button_13),
            findViewById(R.id.button_21), findViewById(R.id.button_22), findViewById(R.id.button_23),
        )

        boardButtons.forEach { btn ->
            btn.setOnClickListener {
                btn.text = if (currentIsX) "X" else "O"
                btn.isEnabled = false
                currentIsX = !currentIsX
            }
        }

    }
}