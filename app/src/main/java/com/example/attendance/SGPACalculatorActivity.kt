package com.example.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SGPACalculatorActivity : AppCompatActivity() {

    private lateinit var blocksLayout: LinearLayout
    private lateinit var addBlockButton: Button
    private lateinit var calculateButton: Button
    private lateinit var sgpaResultTextView: TextView

    private val blocks = ArrayList<Block>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sgpacalculator)

        blocksLayout = findViewById(R.id.blocksLayout)
        addBlockButton = findViewById(R.id.addBlockButton)
        calculateButton = findViewById(R.id.calculateButton)
        sgpaResultTextView = findViewById(R.id.sgpaResultTextView)

        addBlockButton.setOnClickListener {
            addBlock()
        }

        calculateButton.setOnClickListener {
            calculateSGPA()
        }
    }

    private fun addBlock() {
        val blockView = LayoutInflater.from(this).inflate(R.layout.layout_block, null)
        blocksLayout.addView(blockView)

        val block = Block()
        block.pointerEditText = blockView.findViewById(R.id.pointerEditText)
        block.creditEditText = blockView.findViewById(R.id.creditEditText)
        blocks.add(block)
    }

    private fun calculateSGPA() {
        var totalWeightedSum = 0.0
        var totalCredits = 0

        for (block in blocks) {
            val pointer = block.pointerEditText.text.toString().toDouble()
            val credits = block.creditEditText.text.toString().toInt()
            totalWeightedSum += pointer * credits
            totalCredits += credits
        }

        if (totalCredits == 0) {
            sgpaResultTextView.text = "SGPA: N/A"
        } else {
            val sgpa = totalWeightedSum / totalCredits
            sgpaResultTextView.text = "SGPA: $sgpa"
        }
    }

    private inner class Block {
        lateinit var pointerEditText: EditText
        lateinit var creditEditText: EditText
    }
}
