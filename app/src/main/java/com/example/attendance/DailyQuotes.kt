package com.example.attendance

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DailyQuotes: AppCompatActivity() {

    private lateinit var quotestextView: TextView
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.quotable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an instance of QuotableApiService
    private val quotableApiService: QuotableApiService = retrofit.create(QuotableApiService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_quotes)

        quotestextView = findViewById(R.id.QuotestextView)

        displayNewOrStoredQuote()
    }

    private fun displayNewOrStoredQuote() {
        val sharedPrefs = getSharedPreferences("DailyQuotePrefs", Context.MODE_PRIVATE)
        val lastDisplayedTimestamp = sharedPrefs.getLong("lastDisplayedTimestamp", 0L)

        if (has24HoursPassed(lastDisplayedTimestamp)) {
            fetchAndDisplayNewQuote(sharedPrefs)
        } else {
            val storedQuote = sharedPrefs.getString("lastDisplayedQuote", "")
            quotestextView.text = storedQuote
        }
    }

    private fun has24HoursPassed(lastDisplayedTimestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - lastDisplayedTimestamp >= 24 * 60 * 60 * 1000 // 24 hours in milliseconds
    }
    private fun fetchAndDisplayNewQuote(sharedPrefs: SharedPreferences) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val quoteResponse = quotableApiService.getRandomQuote()

                if (quoteResponse.isSuccessful) {
                    val newQuote = quoteResponse.body()?.content // Access the content property here

                    newQuote?.let {
                        quotestextView.text = it

                        val currentTime = System.currentTimeMillis()
                        sharedPrefs.edit().putLong("lastDisplayedTimestamp", currentTime).apply()
                        sharedPrefs.edit().putString("lastDisplayedQuote", it).apply()
                    }
                } else {
                    // Handle unsuccessful response
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}
