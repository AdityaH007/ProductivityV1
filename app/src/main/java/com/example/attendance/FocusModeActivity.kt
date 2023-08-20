package com.example.attendance

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FocusModeActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var stopwatchTextView: TextView
    private lateinit var handler: Handler
    private var isFocusModeActive = false
    private var elapsedTime: Long = 0

    private var isMinimizingBlocked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_mode)

        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        stopwatchTextView = findViewById(R.id.stopwatchTextView)
        handler = Handler()

        startButton.setOnClickListener {
            // ... (Check notification policy access permission)

            startFocusMode()
        }

        stopButton.setOnClickListener {
            stopFocusMode()
        }
    }

    private fun startFocusMode() {
        startButton.isEnabled = false
        stopButton.isEnabled = true
        elapsedTime = 0
        isFocusModeActive = true
        stopwatchTextView.visibility = View.VISIBLE
        updateStopwatch()
    }

    private fun stopFocusMode() {
        startButton.isEnabled = true
        stopButton.isEnabled = false
        isFocusModeActive = false
        stopwatchTextView.visibility = View.GONE
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateStopwatch() {
        if (isFocusModeActive) {
            val hours = elapsedTime / 3600000
            val minutes = (elapsedTime % 3600000) / 60000
            val seconds = (elapsedTime % 60000) / 1000

            val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            stopwatchTextView.text = timeString

            elapsedTime += 1000 // Update every second
            handler.postDelayed({ updateStopwatch() }, 1000)
        }
    }

    override fun onBackPressed() {
        if (isFocusModeActive) {
            showFocusModeActiveDialog()
        } else {
            super.onBackPressed()
        }
    }

    override fun onUserLeaveHint() {
        if (isFocusModeActive) {
            showFocusModeActiveDialog()
        } else {
            super.onUserLeaveHint()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFocusModeActive && isMinimizingBlocked) {
            showFocusModeActiveDialog()
        }
    }

    private fun showFocusModeActiveDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Focus Mode Active")
            .setMessage("Please stop the focus mode before minimizing or going back.")
            .setPositiveButton("OK") { _, _ ->
                isMinimizingBlocked = false
            }
            .setCancelable(false)
            .create()

        // Set a listener to prevent minimizing while the dialog is shown
        dialog.setOnShowListener {
            isMinimizingBlocked = true
        }

        dialog.show()
    }

    override fun onStop() {
        super.onStop()
        if (isFocusModeActive) {
            stopFocusMode()
        }
    }
}
