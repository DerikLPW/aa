package com.example.phone

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lcv.bgColor = Color.GRAY
        lcv.items = floatArrayOf(
            60f,
            100f,
            0f,
            23f,
            170f,
            50f,
            10f
        )
        lcv.setOnClickListener{
            println("lcv clicked")
        }
    }
}
