package com.example.bigtest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class full_persone_info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_persone_info)

        val fullInfo: TextView = findViewById(R.id.full_info_view)

        fullInfo.text = intent.getStringExtra("info")
    }
}