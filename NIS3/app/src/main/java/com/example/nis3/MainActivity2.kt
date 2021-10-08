package com.example.nis3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val buttonPrev: Button = findViewById(R.id.prevButton)
        val userText: TextView = findViewById(R.id.userInput)
//        userText.text = MainActivity.rememberText


        userText.text = intent.getStringExtra("textForPrint").toString()
        buttonPrev.setOnClickListener(View.OnClickListener {
            finish()
//            val intent: Intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("textForPrint", userText.text.toString())
//            startActivity(intent)
        })
    }
}