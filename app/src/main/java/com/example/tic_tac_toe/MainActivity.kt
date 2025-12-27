package com.example.tic_tac_toe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {

    private lateinit var status: TextView
    private lateinit var restartBtn: Button
    private lateinit var boardButtons: List<Button>
    private var currentIsX = true
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.player_turn_indicator)
        restartBtn = findViewById(R.id.restart_button)

        boardButtons = listOf(
            findViewById(R.id.button_01), findViewById(R.id.button_02), findViewById(R.id.button_13), // top row
            findViewById(R.id.button_11), findViewById(R.id.button_12), findViewById(R.id.button_03), // middle row
            findViewById(R.id.button_21), findViewById(R.id.button_22), findViewById(R.id.button_23)  // bottom row
        )

        boardButtons.forEach { btn ->
            btn.setOnClickListener {
                if (gameOver || btn.text.isNotEmpty()) return@setOnClickListener

                btn.text = if (currentIsX) "X" else "O"
                val color = if (currentIsX) "#FF0000" else "#0000FF"
                btn.setTextColor(color.toColorInt())

                val winner = checkWin()
                if (winner != null) {
                    status.text = "Player \"$winner\" wins"
                    // Keep the winning color on the winning player
                    val winnerColor = if (winner == "X") "#FF0000" else "#0000FF"
                    status.setTextColor(winnerColor.toColorInt())
                    gameOver = true
                } else if (boardButtons.all { it.text.isNotEmpty() }) {
                    status.text = "Draw"
                    gameOver = true
                } else {
                    // --- Switch to the next turn ---
                    currentIsX = !currentIsX
                    status.text = "Player \"${if (currentIsX) "X" else "O"}\" Turn"
                    // Set the status color for the *new* current player
                    val nextPlayerColor = if (currentIsX) "#FF0000" else "#0000FF"
                    status.setTextColor(nextPlayerColor.toColorInt())
                }
            }
        }

        restartBtn.setOnClickListener { resetBoard() }

        // initialize status
        resetBoard()
    }

    private fun resetBoard() {
        boardButtons.forEach { b ->
            b.text = ""
            b.isEnabled = true
        }
        currentIsX = true
        gameOver = false
        status.text = "Player \"X\" Turn"
    }

    private fun checkWin(): String? { // It now returns the winner as a String, or null
        val b = boardButtons
        val lines = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )
        for (line in lines) {
            val a = b[line[0]].text.toString()
            val c = b[line[1]].text.toString()
            val d = b[line[2]].text.toString()
            if (a.isNotEmpty() && a == c && c == d) {
                return a // Return the winning symbol ("X" or "O")
            }
        }
        return null // Return null if there is no winner
    }
}