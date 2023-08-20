package com.example.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button

class home : AppCompatActivity() {
    private lateinit var atd:Button
    private lateinit var dt:Button
    private lateinit var f:Button
    private lateinit var abt:Button
    private lateinit var calc:Button
    private lateinit var DQ:Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        atd=findViewById(R.id.atm)
        atd.setOnClickListener {
            val intent=Intent(this,SubjectManagementActivity::class.java)
            startActivity(intent)
        }

        dt=findViewById(R.id.dt)
        dt.setOnClickListener {
            val intent=Intent(this,DailyActivity::class.java)
            startActivity(intent)
        }

        f=findViewById(R.id.f)
        f.setOnClickListener {
            val intent=Intent(this,FocusModeActivity::class.java)
            startActivity(intent)
        }

        abt=findViewById(R.id.abt)
        abt.setOnClickListener {
            val intent=Intent(this,about::class.java)
            startActivity(intent)
        }

        calc=findViewById(R.id.calc)
        calc.setOnClickListener {
            val intent =Intent(this,SGPACalculatorActivity::class.java)
            startActivity(intent)
        }
        DQ=findViewById(R.id.dq)
        DQ.setOnClickListener {
            val intent=Intent(this,DailyQuotes::class.java)
            startActivity(intent)
        }
    }
}